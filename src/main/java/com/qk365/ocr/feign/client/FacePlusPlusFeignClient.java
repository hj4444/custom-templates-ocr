package com.qk365.ocr.feign.client;

import com.qk365.ocr.model.dto.faceplusplus.RecognizeTextResp;
import feign.Headers;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

@FeignClient(name = "${ocr.facePlusPlus.apiUrl}")
public interface FacePlusPlusFeignClient {
    @RequestLine("POST")
    @Headers("Content-Type: multipart/form-data")
    RecognizeTextResp getOcrTemplateInfo(Map<String, ?> queryParams);
}
