package com.qk365.ocr.model.dto.faceplusplus;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Setter
@Getter
public class ValueResult {
    private List<String> text;

    @Override
    public String toString() {
        return StringUtils.joinWith(",", text.toArray());
    }
}
