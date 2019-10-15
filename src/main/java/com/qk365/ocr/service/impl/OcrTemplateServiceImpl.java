package com.qk365.ocr.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qk365.ocr.constant.CommonConstant;
import com.qk365.ocr.mapper.OcrTemplateMapper;
import com.qk365.ocr.model.bean.OcrOpLog;
import com.qk365.ocr.model.bean.OcrTemplate;
import com.qk365.ocr.model.bean.TemplateResponseSchema;
import com.qk365.ocr.model.bo.OcrTemplateBo;
import com.qk365.ocr.model.dto.R;
import com.qk365.ocr.model.dto.ResultCodeEnum;
import com.qk365.ocr.service.LoggerService;
import com.qk365.ocr.service.OcrService;
import com.qk365.ocr.service.OcrServiceFactory;
import com.qk365.ocr.service.OcrTemplateService;
import com.qk365.ocr.util.CollectionUtil;
import com.qk365.ocr.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OcrTemplateServiceImpl implements OcrTemplateService {
    @Autowired
    private OcrTemplateMapper ocrTemplateMapper;
    @Autowired
    private LoggerService loggerService;

    @Cacheable(value = "getAllOcrTemplates")
    @Override
    public List<OcrTemplateBo> getAllOcrTemplates() {
        List<OcrTemplate> ocrTemplateList = ocrTemplateMapper.getAllOcrTemplates();
        List<OcrTemplateBo> ocrTemplateBoList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(ocrTemplateList)) {
            ocrTemplateList.stream().forEach(tmpl -> {
                OcrTemplateBo ocrTemplateBo = new OcrTemplateBo();
                ocrTemplateBo.setOcrType(tmpl.getOcrType());
                ocrTemplateBo.setTemplateId(tmpl.getTemplateId());
                JSONArray jsonArray = tmpl.getTplRespSchemaJsonArray();
                List<TemplateResponseSchema> templateResponseSchemaList = new ArrayList<>();
                if (CollectionUtil.isNotEmpty(jsonArray)) {
                    jsonArray.stream().forEach(f -> {
                        TemplateResponseSchema templateResponseSchema = new TemplateResponseSchema();
                        JSONObject jsonObject = (JSONObject) f;
                        templateResponseSchema.setFieldName(jsonObject.getString(CommonConstant.TEMPLATE_OCR_RESPONSE_SCHEMA_FIELD_NAME));
                        templateResponseSchema.setFieldType(jsonObject.getString(CommonConstant.TEMPLATE_OCR_RESPONSE_SCHEMA_FIELD_TYPE));
                        templateResponseSchemaList.add(templateResponseSchema);
                    });
                }
                ocrTemplateBo.setTemplateResponseSchemaList(templateResponseSchemaList);
                ocrTemplateBoList.add(ocrTemplateBo);
            });
        }
        return ocrTemplateBoList;
    }

    @SentinelResource(value = "template.ocr.rule", blockHandler = "getTemplateOcrBlockHandler", fallback = "getTemplateOcrFallback")
    public R getTemplateOcrResponse(String templateId, String imageBase64) {
        List<OcrTemplateBo> ocrTemplateList = getAllOcrTemplates();
        if (CollectionUtil.isEmpty(ocrTemplateList)) {
            return R.fail("没有获取到任何ocr模版信息");
        }
        Optional<OcrTemplateBo> ocrTemplateOptional = ocrTemplateList.stream().filter(f -> templateId.equals(f.getTemplateId())).findFirst();
        if (ocrTemplateOptional.isPresent()) {
            OcrTemplateBo ocrTemplate = ocrTemplateOptional.get();
            OcrService ocrService = null;
            try {
                ocrService = OcrServiceFactory
                        .getStrategy(ocrTemplate.getOcrType());
            } catch (Exception e) {
                log.error(LogUtil.getPrintExceptionString(e));
            }
            R result = ocrService.getTemplateOcrResponse(templateId, imageBase64, ocrTemplate);
            loggerService.SenderLog(new OcrOpLog(templateId, result.getData().toString(), ocrTemplate.getOcrType()));
            return result;
        } else {
            return R.fail(String.format("没有获取到该%s ocr模版信息", templateId));
        }
    }

    public R getTemplateOcrBlockHandler(String templateId, String imageBase64, BlockException ex) {
        log.error(LogUtil.getPrintExceptionString(ex));
        return R.fail(ResultCodeEnum.INTERNAL_SERVER_ERROR);
    }

    public R getTemplateOcrFallback(String templateId, String imageBase64) {
        return R.fail(ResultCodeEnum.NOT_FOUND);
    }

}
