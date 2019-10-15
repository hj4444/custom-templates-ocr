package com.qk365.ocr.disruptor;

import com.lmax.disruptor.EventFactory;

public class LoggerEventFactory implements EventFactory<LoggerEvent> {
    @Override
    public LoggerEvent newInstance() {
        return new LoggerEvent();
    }
}
