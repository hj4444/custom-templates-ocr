package com.qk365.ocr.Controller;

import com.qk365.ocr.model.dto.R;
import com.qk365.ocr.service.OcrTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/ocr")
public class OcrController {
    @Autowired
    private OcrTemplateService ocrTemplateService;

    @PostMapping(value = "/templateOcr/{templateId}")
    @ResponseBody
    public R getTemplateOcr(@Valid @PathVariable String templateId, @Valid @RequestBody String imageBase64) {
        return ocrTemplateService.getTemplateOcrResponse(templateId, imageBase64);
    }
}
