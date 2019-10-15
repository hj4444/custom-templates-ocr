package com.qk365.ocr.service;

import com.qk365.ocr.model.bo.OcrTemplateBo;
import com.qk365.ocr.model.dto.R;

public interface OcrService {
    R getTemplateOcrResponse(String templateId, String imageBase64, OcrTemplateBo ocrTemplate);
}
