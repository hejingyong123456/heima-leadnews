package com.heima.common.baidu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import javax.servlet.http.HttpUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "baidu")
public class GreenTextScan {

    private final static String appId = "36490164";
    private final static String appKey = "zGobYIqRWkoGIIPnkkHDtxK7";
    private final static String secretKey = "SSrNfhXhp1CVq8tIePzO1IUZxT5AUmEA";
    public static String baiduAccessToken = "24.26563cbe8cc9891ef98b6d2890bdd204.2592000.1692588473.282335-36490164";


    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    public static void setBaiduAccessToken() {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token?client_id="+appKey+"&client_secret="+secretKey+"&grant_type=client_credentials")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        Response response = null;
        try {
            response = HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = JSON.parseObject(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        baiduAccessToken = jsonObject.getString("access_token");
    }




    public static JSONObject sendPost(String msg) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "text="+msg);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/solution/v1/text_censor/v2/user_defined?access_token=" +baiduAccessToken)
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept", "application/json")
                .build();
        Response response = null;

            response = HTTP_CLIENT.newCall(request).execute();

            if(response.code()==200){
                JSONObject jsonObject = null;

                    jsonObject = JSON.parseObject(response.body().string());

                return jsonObject;
            }

        return null;
    }


    public Map greeTextScan(String text) throws IOException {
        if(StringUtils.isEmpty(text)){
            return null;
        }
        Map<String, String> resultMap = new HashMap<>();
       JSONObject jsonObject=sendPost(text);
        String suggestion = jsonObject.getString("conclusion");
        if (!suggestion.equals("合规")) {
            resultMap.put("suggestion", suggestion);

            JSONArray taskResults = jsonObject.getJSONArray("data");
            JSONObject jsonObject1 = JSON.parseObject(String.valueOf(taskResults.get(0)));

            String label = jsonObject1.getString("msg");
            resultMap.put("label", label);
            return resultMap;
        }
            resultMap.put("suggestion", "pass");
            return resultMap;
        }


}
