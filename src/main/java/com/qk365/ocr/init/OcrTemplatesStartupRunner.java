package com.qk365.ocr.init;

import com.qk365.ocr.service.OcrTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OcrTemplatesStartupRunner implements CommandLineRunner {
    @Autowired
    private OcrTemplateService ocrTemplateService;


    @Override
    public void run(String... args) throws Exception {
        ocrTemplateService.getAllOcrTemplates();
    }
}
