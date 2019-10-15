package com.qk365.ocr.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import com.qk365.ocr.mapper.OcrOpLogMapper;
import org.springframework.stereotype.Component;

@Component
public class LoggerEventHandler implements EventHandler<LoggerEvent>, WorkHandler<LoggerEvent> {
    private OcrOpLogMapper ocrOpLogMapper;

    public LoggerEventHandler(OcrOpLogMapper ocrOpLogMapper) {
        this.ocrOpLogMapper = ocrOpLogMapper;
    }

    @Override
    public void onEvent(LoggerEvent loggerEvent, long l, boolean b) throws Exception {
        this.onEvent(loggerEvent);
    }

    @Override
    public void onEvent(LoggerEvent loggerEvent) throws Exception {
        ocrOpLogMapper.insert(loggerEvent.getOcrOpLog());
    }
}
