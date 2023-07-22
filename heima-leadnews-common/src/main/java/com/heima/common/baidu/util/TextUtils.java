//package com.heima.common.baidu.util;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import lombok.extern.slf4j.Slf4j;
//import okhttp3.*;
//import org.springframework.util.StringUtils;
//
//
//import javax.servlet.http.HttpUtils;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//public class TextUtils {
//
//    private final static String appId = "36490164";
//    private final static String appKey = "zGobYIqRWkoGIIPnkkHDtxK7";
//    private final static String secretKey = "SSrNfhXhp1CVq8tIePzO1IUZxT5AUmEA";
//    private final static String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
//    private final static String textHttp="https://aip.baidubce.com/rest/2.0/solution/v1/text_censor/v2/user_defined";
//    public static String baiduAccessToken = null;
//
//
////    public static void setBaiduAccessToken(){
////        // 获取token地址
////        String getAccessTokenUrl = authHost
////                // 1. grant_type为固定参数
////                + "grant_type=client_credentials"
////                // 2. 官网获取的 API Key
////                + "&client_id=" + appKey
////                // 3. 官网获取的 Secret Key
////                + "&client_secret=" + secretKey;
////        BufferedReader in = null;
////        try {
////            URL realUrl = new URL(getAccessTokenUrl);
////            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
////            connection.setRequestMethod("GET");
////            connection.connect();
////            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
////            String result = "";
////            String line;
////            while ((line = in.readLine()) != null) {
////                result += line;
////            }
////            /**
////             * 返回结果示例
////             */
////            log.info("result:" + result);
////            JSONObject jsonObject = JSON.parseObject(result);
////            baiduAccessToken = jsonObject.getString("access_token");
////        } catch (Exception e) {
////            log.error("获取token失败:{}",e.toString());
////        }finally {
////            try {
////                if(in != null){
////                    in.close();
////                }
////            } catch (IOException e) {
////                log.error("获取百度文字检查token失败:{}",e.toString());
////            }
////        }
////    }
//
//
//    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();
//
//    public static Response get() throws IOException {
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, "");
//        Request request = new Request.Builder()
//                .url("https://aip.baidubce.com/oauth/2.0/token?client_id="+appId+"&client_secret="+appKey+"&grant_type=client_credentials")
//                .method("POST", body)
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Accept", "application/json")
//                .build();
//        Response response = HTTP_CLIENT.newCall(request).execute();
//        System.out.println(response.body().string());
//        return response;
//    }
////    /**
////     * 检测
////     * @param param
////     * @return
////     */
////    public static JSONObject sendPost(String param) {
////        if(StringUtils.isEmpty(baiduAccessToken)){
////            setBaiduAccessToken();
////        }
////        param = param.replaceAll(" ","%2B");
////        JSONObject result = HttpUtils.doPostNoHeader(textHttp+"?access_token="+baiduAccessToken+"&text="+param,null);
////        return result;
////    }
//
////    /**
////     * 检查文本是否合规
////     * @param text
////     * @return
////     */
////    public static String checkText(String text){
////        if(StringUtils.isEmpty(text)){
////            return null;
////        }
////        JSONObject result = TextUtils.sendPost(text);
////        if(result.getLong("error_code") != null){
////            return result.getString("error_msg");
////        }
////        if(result.getIntValue("conclusionType") == 1){
////            // 合规
////            return null;
////        }
////        return result.getJSONArray("data").getJSONObject(0).getString("msg");
////    }
//
//    public static void main(String[] args) throws IOException {
////        setBaiduAccessToken();
//        String text = "违规字样";
////        JSONObject result = sendPost(text.replaceAll(" ","%2B"));
////        System.out.println(result.toJSONString());
//        System.out.println(get());
//    }
//
//}
