package com.qk365.ocr.service;

import com.qk365.ocr.model.bo.OcrTemplateBo;

public class OcrServiceContext {
    private OcrService ocrService;

    public OcrServiceContext(OcrService ocrService) {
        this.ocrService = ocrService;
    }

    public void getTemplateOcrResponse(String templateId, String imageBase64, OcrTemplateBo ocrTemplate) {
        this.ocrService.getTemplateOcrResponse(templateId, imageBase64, ocrTemplate);
    }
}
