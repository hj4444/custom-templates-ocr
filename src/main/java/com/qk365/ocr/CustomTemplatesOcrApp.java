package com.qk365.ocr;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.qk365.ocr.config.OcrConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties(value = OcrConfig.class)
@MapperScan(basePackages = "com.qk365.ocr.mapper")
@EnableApolloConfig
public class CustomTemplatesOcrApp {
    public static void main(String[] args) {
        SpringApplication.run(CustomTemplatesOcrApp.class, args);
    }
}
