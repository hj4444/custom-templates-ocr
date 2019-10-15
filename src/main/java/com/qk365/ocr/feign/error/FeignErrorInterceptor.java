package com.qk365.ocr.feign.error;

import com.qk365.ocr.exception.FeignException;
import com.qk365.ocr.util.LogUtil;
import com.qk365.ocr.util.StringUtil;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static feign.Util.UTF_8;

@Slf4j
public class FeignErrorInterceptor implements ErrorDecoder {
    private ErrorDecoder delegate = new ErrorDecoder.Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        byte[] responseBody;
        String body = "";
        try {
            responseBody = Util.toByteArray(response.body().asInputStream());
            body = new String(responseBody, UTF_8);
        } catch (IOException e) {
            log.error(LogUtil.getPrintExceptionString(e));
            throw new RuntimeException("Failed to process response body.", e);
        }
        if (response.status() >= 400 && response.status() <= 599) {
            String trimBody = StringUtil.trim(body, '\n');
            log.error(trimBody);
            return new FeignException(response.status(), trimBody);
        }
        return delegate.decode(methodKey, response);
    }
}
