package com.qk365.ocr.model.bean;

import com.alibaba.fastjson.JSONArray;
import com.qk365.ocr.model.bean.enums.OcrType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OcrTemplate {
    private String templateId;
    private OcrType ocrType;
    private JSONArray tplRespSchemaJsonArray;
}
