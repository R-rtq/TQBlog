package com.rtq.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author rtq
 * @Date 2023/1/3
 **/
@Component
public class TestJob {

    //@Scheduled(cron = "0/5 * * * * ?")
    public void testJob(){
        //要执行的代码
        System.out.println("定时任务。。 ");
    }
}
