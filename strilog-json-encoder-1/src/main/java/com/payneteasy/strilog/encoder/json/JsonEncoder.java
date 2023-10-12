package com.payneteasy.strilog.encoder.json;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.encoder.EncoderBase;

import static com.payneteasy.strilog.encoder.core.LogEvents.EMPTY_BYTES;

public class JsonEncoder<E> extends EncoderBase<E> {

    private JsonLayout layout;

    @Override
    public byte[] headerBytes() {
        return EMPTY_BYTES;
    }

    @Override
    public byte[] encode(E e) {
        return layout.doLayout((ILoggingEvent) e);
    }

    @Override
    public void start() {
        layout = new JsonLayout(
                null
                , null
                , context.getProperty("app-name")
                , context.getProperty("app-instance")
        );
        super.start();
    }

    @Override
    public byte[] footerBytes() {
        return EMPTY_BYTES;
    }
}
