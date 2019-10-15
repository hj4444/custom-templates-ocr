package com.qk365.ocr.model.dto.faceplusplus;

import com.qk365.ocr.util.CollectionUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Setter
@Getter
public class RecognizeTextResp {
    private int time_used;
    private String request_id;
    private String error_message;
    private List<KeyResult> result;

    public String getTextByKey(String key) {
        if (CollectionUtil.isNotEmpty(result)) {
            Optional<KeyResult> keyResultOptional = result.stream().filter(f -> key.equals(f.getKey())).findFirst();
            if (keyResultOptional.isPresent()) {
                return keyResultOptional.get().getValue().toString();
            }
        }

        return "";
    }
}
