package com.qk365.ocr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qk365.ocr.model.bean.OcrTemplate;

import java.util.List;

public interface OcrTemplateMapper extends BaseMapper<OcrTemplate> {
    List<OcrTemplate> getAllOcrTemplates();
}
