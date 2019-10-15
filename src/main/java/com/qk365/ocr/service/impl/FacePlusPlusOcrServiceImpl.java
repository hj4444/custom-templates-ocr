package com.qk365.ocr.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.qk365.ocr.config.OcrConfig;
import com.qk365.ocr.constant.CommonConstant;
import com.qk365.ocr.feign.client.FacePlusPlusFeignClient;
import com.qk365.ocr.feign.encoder.FeignSpringFormEncoder;
import com.qk365.ocr.feign.error.FeignErrorInterceptor;
import com.qk365.ocr.model.bean.TemplateResponseSchema;
import com.qk365.ocr.model.bo.OcrTemplateBo;
import com.qk365.ocr.model.dto.R;
import com.qk365.ocr.model.dto.ResultCodeEnum;
import com.qk365.ocr.model.dto.faceplusplus.RecognizeTextResp;
import com.qk365.ocr.service.OcrService;
import com.qk365.ocr.util.ApplicationContextUtil;
import com.qk365.ocr.util.CollectionUtil;
import feign.*;
import feign.httpclient.ApacheHttpClient;
import feign.jackson.JacksonDecoder;
import feign.slf4j.Slf4jLogger;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;

@Slf4j
public class FacePlusPlusOcrServiceImpl implements OcrService {
    private static OcrConfig ocrConfig;
    private static FacePlusPlusFeignClient facePlusPlusFeignClient;

    static {
        ocrConfig = ApplicationContextUtil.getBean(OcrConfig.class);
        facePlusPlusFeignClient = Feign.builder().client(new ApacheHttpClient()).errorDecoder(new FeignErrorInterceptor()).encoder(new FeignSpringFormEncoder())
                .contract(new Contract.Default()).logLevel(Logger.Level.FULL).decoder(new JacksonDecoder()).logger(new Slf4jLogger(log.getClass()))
                .options(new Request.Options(3000, 20000)).retryer(new Retryer.Default(5000, 5000, 3))
                .target(FacePlusPlusFeignClient.class, ocrConfig.getFacePlusPlus().getApiUrl());
    }

    @Override
    public R getTemplateOcrResponse(String templateId, String imageBase64, OcrTemplateBo ocrTemplate) {
        HashMap<String, String> param = Maps.newHashMap();
        param.put("image_base64", imageBase64);
        param.put("template_id", templateId.toLowerCase());
        param.put(CommonConstant.FACE_PLUS_PLUS_API_KEY, ocrConfig.getFacePlusPlus().getApiKey());
        param.put(CommonConstant.FACE_PLUS_PLUS_API_SECRET, ocrConfig.getFacePlusPlus().getApiSecret());
        RecognizeTextResp recognizeTextResp = facePlusPlusFeignClient.getOcrTemplateInfo(param);
        JSONObject schemaObj = this.buildOcrSchemaObject(ocrTemplate, recognizeTextResp);
        return R.success(ResultCodeEnum.SUCCESS).data(schemaObj);
    }

    private JSONObject buildOcrSchemaObject(OcrTemplateBo ocrTemplate, RecognizeTextResp recognizeTextResp) {
        JSONObject jsonObject = new JSONObject();
        List<TemplateResponseSchema> templateResponseSchemaList = ocrTemplate.getTemplateResponseSchemaList();
        if (CollectionUtil.isEmpty(templateResponseSchemaList)) return jsonObject;
        templateResponseSchemaList.stream().forEach(schema -> {
            jsonObject.put(schema.getFieldName(), recognizeTextResp.getTextByKey(schema.getFieldName()));
        });

        return jsonObject;
    }
}
