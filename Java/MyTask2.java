package com.wise.gemmes.processroute.util;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 基于注解的定时器
 */
@Component
public class MyTask2 {

    /**
     * 定时计算。每天凌晨 01:00 执行一次
     */
    @Scheduled(cron = "0 0 1 * * *")
    public void show() {
        System.out.println("show method 2");
    }

    /**
     * 启动时执行一次，之后每隔60秒执行一次
     */
    @Scheduled(fixedRate = 1000*60)
    public void print() {
        System.out.println("print method 60");
    }
}
