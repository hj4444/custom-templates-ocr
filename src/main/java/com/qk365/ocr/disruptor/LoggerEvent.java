package com.qk365.ocr.disruptor;

import com.qk365.ocr.model.bean.OcrOpLog;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoggerEvent {
    private OcrOpLog ocrOpLog;
}
