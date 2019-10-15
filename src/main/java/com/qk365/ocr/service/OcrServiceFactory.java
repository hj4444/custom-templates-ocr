package com.qk365.ocr.service;

import com.qk365.ocr.model.bean.enums.OcrType;
import com.qk365.ocr.util.ClassUtil;

public class OcrServiceFactory {
    public static OcrService getStrategy(OcrType ocrType) throws Exception {
        String className = ocrType.getClassName();
        return (OcrService) ClassUtil.instantiateObject(className);
    }
}
