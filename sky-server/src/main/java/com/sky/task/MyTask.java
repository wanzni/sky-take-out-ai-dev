package com.sky.task;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 王众
 * @date 2025/9/19 19:01
 * @description
 */
@Component
@Slf4j
public class MyTask {

    //@Scheduled(cron = "0/5 * * * * ? ")
    public void executeTask(){
        log.info("定时任务开始执行:{}",new Date());
    }
}
