package com.qk365.ocr.feign.client;

import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "${ocr.aliyun.apiUrl}")
public interface AliyunFeignClient {
    @RequestLine("POST")
    String getOcrTemplateInfo(String queryParams);
}

