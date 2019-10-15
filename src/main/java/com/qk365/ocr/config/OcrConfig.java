package com.qk365.ocr.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "ocr")
public class OcrConfig {
    private Aliyun aliyun;
    private FacePlusPlus facePlusPlus;

    @Setter
    @Getter
    public static class Aliyun {
        private String apiUrl;
        private String appCode;
    }

    @Setter
    @Getter
    public static class FacePlusPlus {
        private String apiUrl;
        private String apiKey;
        private String apiSecret;
    }
}

