package com.rtq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author rtq
 * @Date 2022/12/22
 **/
@MapperScan("com.rtq.mapper")
@SpringBootApplication
@EnableSwagger2
//开启定时任务
@EnableScheduling
public class TQBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(TQBlogApplication.class,args);
    }
}
