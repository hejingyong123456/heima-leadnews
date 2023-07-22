//package com.heima.common.baidu;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.util.TextUtils;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class BaiduAccessTokenTask {
//
//    /**
//     * 每周一凌晨
//     */
//    @Scheduled(cron = "0 0 0 ? * MON")
//    public void xxx() {
//        log.info("----百度TOKEN定时任务每周一凌晨执行开始-----");
//        TextUtils.setBaiduAccessToken();
//        log.info("----百度TOKEN定时任务每周一凌晨执行结束-----");
//    }
//}
