package com.qk365.ocr.feign.error;

import com.qk365.ocr.exception.FeignException;
import com.qk365.ocr.util.StringUtil;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

import static feign.Util.UTF_8;

@Slf4j
public class FeignErrorInterceptor implements ErrorDecoder {
    private ErrorDecoder delegate = new ErrorDecoder.Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        byte[] responseBody;
        String body = "" ;
        try {
            responseBody = Util.toByteArray(response.body().asInputStream());
            body = new String(responseBody,UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to process response body.", e);
        }
        if (response.status() >= 400 && response.status() <= 599) {
            return new FeignException(response.status(), StringUtil.trim(body,'\n'));
        }
        return delegate.decode(methodKey, response);
    }
}
