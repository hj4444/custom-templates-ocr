package com.qk365.ocr;

import com.alibaba.fastjson.JSONObject;
import com.qk365.ocr.model.dto.R;
import com.qk365.ocr.service.OcrTemplateService;
import com.qk365.ocr.util.Base64Util;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OcrTest {
    @Test
    public void JsonObjectTest() {
        String sqlJson = "[{\"fieldName\": \"accountName\", \"fieldType\": \"string\"}, {\"fieldName\": \"amount\", \"fieldType\": \"string\"}]";
        JSONObject js = JSONObject.parseObject(sqlJson);
        Assert.assertEquals(js == null, "");
    }
    @Autowired
    private OcrTemplateService ocrTemplateService;
    @Test
    public void AliyunOcrTest(){
        String imageBase64 = Base64Util.getImageStr("C:\\Users\\0200222\\Documents\\Tencent Files\\315017197\\FileRecv\\松江城区1.jpg");
        R res = ocrTemplateService.getTemplateOcrResponse("c9dc30ea-aace-414b-832b-d0c2778d140e1558941207", imageBase64);
        Assert.assertNotNull(res);
    }

    @Test
    public void FacePlusPlusOcrTest(){
        String imageBase64 = Base64Util.getImageStr("C:\\Users\\0200222\\Pictures\\TIM图片20190925140455.jpg");
        R res = ocrTemplateService.getTemplateOcrResponse("1569391586", imageBase64);
        Assert.assertNotNull(res);
    }
}
