package com.qk365.ocr.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qk365.ocr.config.OcrConfig;
import com.qk365.ocr.feign.client.AliyunFeignClient;
import com.qk365.ocr.feign.error.FeignErrorInterceptor;
import com.qk365.ocr.feign.interceptor.AliyunAuthRestInterceptor;
import com.qk365.ocr.model.bean.TemplateResponseSchema;
import com.qk365.ocr.model.bo.OcrTemplateBo;
import com.qk365.ocr.model.dto.R;
import com.qk365.ocr.model.dto.ResultCodeEnum;
import com.qk365.ocr.service.OcrService;
import com.qk365.ocr.util.ApplicationContextUtil;
import com.qk365.ocr.util.Base64Util;
import com.qk365.ocr.util.CollectionUtil;
import feign.*;
import feign.httpclient.ApacheHttpClient;
import feign.slf4j.Slf4jLogger;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class AliyunOcrServiceImpl implements OcrService {
    private static AliyunFeignClient aliyunFeignClient;
    private static OcrConfig ocrConfig;

    static {
        ocrConfig = ApplicationContextUtil.getBean(OcrConfig.class);
        aliyunFeignClient = Feign.builder().client(new ApacheHttpClient()).errorDecoder(new FeignErrorInterceptor()).requestInterceptor(new AliyunAuthRestInterceptor(ocrConfig))
                .contract(new Contract.Default()).logLevel(Logger.Level.FULL).logger(new Slf4jLogger(log.getClass()))
                .options(new Request.Options(3000, 5000)).retryer(new Retryer.Default(5000, 5000, 3))
                .target(AliyunFeignClient.class, ocrConfig.getAliyun().getApiUrl());
    }

    @Override
    public R getTemplateOcrResponse(String templateId, String imageBase64, OcrTemplateBo ocrTemplate) {
        Integer imageSize = Base64Util.getImageSize(imageBase64);
        if (ocrConfig.getAliyun().getImageSize() < imageSize) {
            return R.fail(ResultCodeEnum.IMAGE_TOO_LARGE_ERROR);
        }
        JSONObject requestObj = new JSONObject();
        requestObj.put("image", imageBase64);
        JSONObject configObj = new JSONObject();
        configObj.put("template_id", templateId.toLowerCase());
        String configStr = configObj.toString();
        requestObj.put("configure", configStr);
        String bodys = requestObj.toString();
        String res = aliyunFeignClient.getOcrTemplateInfo(bodys);
        JSONObject resObj = JSON.parseObject(res);
        JSONObject itemsObj = (JSONObject) resObj.get("items");
        JSONObject schemaObj = this.buildOcrSchemaObject(ocrTemplate, itemsObj);
        return R.success(ResultCodeEnum.SUCCESS).data(schemaObj);
    }

    private JSONObject buildOcrSchemaObject(OcrTemplateBo ocrTemplate, JSONObject itemsObj) {
        JSONObject jsonObject = new JSONObject();
        List<TemplateResponseSchema> templateResponseSchemaList = ocrTemplate.getTemplateResponseSchemaList();
        if (CollectionUtil.isEmpty(templateResponseSchemaList)) {
            return jsonObject;
        }
        templateResponseSchemaList.stream().forEach(schema -> {
            jsonObject.put(schema.getFieldName(), itemsObj.getString(schema.getFieldName()));
        });

        return jsonObject;
    }
}
