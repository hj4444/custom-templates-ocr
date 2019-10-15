package com.qk365.ocr.model.bo;

import com.qk365.ocr.model.bean.TemplateResponseSchema;
import com.qk365.ocr.model.bean.enums.OcrType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OcrTemplateBo {
    private String templateId;
    private OcrType ocrType;
    private List<TemplateResponseSchema> templateResponseSchemaList;
}
