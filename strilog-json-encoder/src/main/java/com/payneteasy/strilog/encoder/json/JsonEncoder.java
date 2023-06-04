package com.payneteasy.strilog.encoder.json;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.encoder.EncoderBase;

public class JsonEncoder<E> extends EncoderBase<E> {

    private final JsonLayout layout = new JsonLayout();

    @Override
    public byte[] headerBytes() {
        return new byte[0];
    }

    @Override
    public byte[] encode(E e) {
        return layout.doLayout((ILoggingEvent) e);
    }

    @Override
    public byte[] footerBytes() {
        return new byte[0];
    }
}
