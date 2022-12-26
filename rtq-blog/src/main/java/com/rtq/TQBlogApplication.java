package com.rtq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author rtq
 * @Date 2022/12/22
 **/
@MapperScan("com.rtq.mapper")
@SpringBootApplication
@EnableSwagger2
public class TQBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(TQBlogApplication.class,args);
    }
}
