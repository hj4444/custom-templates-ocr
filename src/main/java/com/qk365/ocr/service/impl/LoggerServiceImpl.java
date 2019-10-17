package com.qk365.ocr.service.impl;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.qk365.ocr.disruptor.LoggerEvent;
import com.qk365.ocr.disruptor.LoggerEventFactory;
import com.qk365.ocr.disruptor.LoggerEventHandler;
import com.qk365.ocr.exception.LoggerEventHandlerException;
import com.qk365.ocr.mapper.OcrOpLogMapper;
import com.qk365.ocr.model.bean.OcrOpLog;
import com.qk365.ocr.service.LoggerService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;

@Service
public class LoggerServiceImpl implements LoggerService, DisposableBean, InitializingBean {
    private static final int RING_BUFFER_SIZE = 1024 * 1024;
    private Disruptor<LoggerEvent> disruptor;
    @Autowired
    private OcrOpLogMapper ocrOpLogMapper;

    @Override
    public void senderLog(OcrOpLog ocrOpLog) {
        RingBuffer<LoggerEvent> ringBuffer = disruptor.getRingBuffer();
        ringBuffer.publishEvent((event, sequence, data) -> event.setOcrOpLog(data), ocrOpLog);
    }

    @Override
    public void destroy() throws Exception {
        disruptor.shutdown();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        disruptor = new Disruptor<>(new LoggerEventFactory(), RING_BUFFER_SIZE, Executors.defaultThreadFactory(), ProducerType.SINGLE, new BlockingWaitStrategy());
        disruptor.setDefaultExceptionHandler(new LoggerEventHandlerException());
        disruptor.handleEventsWith(new LoggerEventHandler(ocrOpLogMapper));
        disruptor.start();
    }
}
