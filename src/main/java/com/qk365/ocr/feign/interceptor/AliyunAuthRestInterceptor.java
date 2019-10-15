package com.qk365.ocr.feign.interceptor;

import com.qk365.ocr.config.OcrConfig;
import feign.RequestInterceptor;
import feign.RequestTemplate;

public class AliyunAuthRestInterceptor implements RequestInterceptor {

    private OcrConfig ocrConfig;

    public AliyunAuthRestInterceptor(OcrConfig ocrConfig) {
        this.ocrConfig = ocrConfig;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", "APPCODE " + ocrConfig.getAliyun().getAppCode());
    }
}
