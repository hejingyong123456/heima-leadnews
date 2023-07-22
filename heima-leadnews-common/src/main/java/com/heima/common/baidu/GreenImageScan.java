package com.heima.common.baidu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@Component
@ConfigurationProperties(prefix = "baidu")
public class GreenImageScan {

    private final static String appId = "36490164";
    private final static String appKey = "zGobYIqRWkoGIIPnkkHDtxK7";
    private final static String secretKey = "SSrNfhXhp1CVq8tIePzO1IUZxT5AUmEA";

    public static String baiduAccessToken = "24.26563cbe8cc9891ef98b6d2890bdd204.2592000.1692588473.282335-36490164";

    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    public Map imageScan(List<byte[]> imageList) throws IOException {

        for(byte[] bytes : imageList){
            String base64Image = Base64.getEncoder().encodeToString(bytes);
            base64Image=base64Image.replaceAll("\r\n", "");
            base64Image= URLEncoder.encode(base64Image,"utf-8");
            Map reponsemap = send(base64Image);

            if(!"pass".equals(reponsemap.get("suggestion"))){
                System.out.println("单张图片处理失败");
                return null;
            }
        }
        Map<String,String> resultmap=new HashMap();
          resultmap.put("suggestion","pass");
        return resultmap;

    }
    public Map send(String base) throws IOException {
        //发送request
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "image="+base);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/solution/v1/img_censor/v2/user_defined?access_token=" + GreenTextScan.baiduAccessToken)
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept", "application/json")
                .build();
        Response response = null;
        Map<String,String> resultMap=new HashMap<>();
        try {
            response = HTTP_CLIENT.newCall(request).execute();
            System.out.println(response.code());
            if(response.code()==200){
                JSONObject jsonObject = null;
                try {
                    jsonObject = JSON.parseObject(response.body().string());

                    String suggestion = jsonObject.getString("conclusion");

                    //不合规返回为什么不合规
                    if (!"合规".equals(suggestion)) {
                        resultMap.put("suggestion", suggestion);
                        JSONArray taskResults = jsonObject.getJSONArray("data");
                        JSONObject jsonObject1 = JSON.parseObject(String.valueOf(taskResults.get(0)));
                        String label = jsonObject1.getString("msg");
                        resultMap.put("label", label);
                        return resultMap;
                    }
                    resultMap.put("suggestion","pass");
                    return resultMap;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



//    @Test
//    public  void main() {
//        byte[] bytes = fileStorageService.downLoadFile("http://192.168.200.130:9000/leadnews/2023/07/21/0f2f9a65ce6641c6b0d0f1e1a56f2559.png");
//        List<byte[]> a=new ArrayList<>();
//        a.add(bytes);
//        imageScan(a);
//
//    }


}
