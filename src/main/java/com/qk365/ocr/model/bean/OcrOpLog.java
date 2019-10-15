package com.qk365.ocr.model.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qk365.ocr.model.bean.enums.OcrType;
import lombok.Getter;
import lombok.Setter;

@TableName("ocr_op_log")
@Getter
@Setter
public class OcrOpLog {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    @TableField("template_id")
    private String templateId;
    @TableField(value = "response_content")
    private String responseContent;
    @TableField("ocr_type")
    private Integer ocrType;

    public OcrOpLog(String templateId, String responseContent, OcrType ocrType) {
        this.templateId = templateId;
        this.responseContent = responseContent;
        this.ocrType = ocrType.getValue();
    }
}
