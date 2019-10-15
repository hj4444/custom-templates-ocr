package com.qk365.ocr.service;

import com.qk365.ocr.model.bo.OcrTemplateBo;
import com.qk365.ocr.model.dto.R;

import java.util.List;

public interface OcrTemplateService {
    List<OcrTemplateBo> getAllOcrTemplates();

    R getTemplateOcrResponse(String templateId, String imageBase64);
}
