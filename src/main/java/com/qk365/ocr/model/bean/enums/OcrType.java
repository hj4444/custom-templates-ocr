package com.qk365.ocr.model.bean.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum OcrType implements IEnum<Integer> {
    ALIYUN(1, "阿里云","com.qk365.ocr.service.impl.AliyunOcrServiceImpl"),
    FACEPLUSPLUS(2, "face++","com.qk365.ocr.service.impl.FacePlusPlusOcrServiceImpl");
    private Integer value;
    private String desc;
    private String className;

    OcrType(final Integer value, final String desc, final String className) {
        this.value = value;
        this.desc = desc;
        this.className =className;
    }

    public static OcrType codeOf(int code) {
        for (OcrType state : values()) {
            if (state.getValue() == code) {
                return state;
            }
        }
        return null;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
