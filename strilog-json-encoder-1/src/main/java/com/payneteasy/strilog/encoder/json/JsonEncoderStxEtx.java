package com.payneteasy.strilog.encoder.json;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.encoder.EncoderBase;
import com.payneteasy.strilog.encoder.core.CycleGenerator;

import static com.payneteasy.strilog.encoder.core.Hostnames.getHostname;
import static com.payneteasy.strilog.encoder.core.LogEvents.EMPTY_BYTES;

public class JsonEncoderStxEtx<E> extends EncoderBase<E> {

    private JsonLayout layout;

    @Override
    public void start() {
        layout = new JsonLayout(
                  new byte[] {0x02, 0x20} // STX 0
                , new byte[] {0x03}       // ETX 00
                , context.getProperty("app-name")
                , context.getProperty("app-instance")
                , getHostname()
                , new CycleGenerator()
        );
        super.start();
    }

    @Override
    public byte[] headerBytes() {
        return EMPTY_BYTES;
    }

    @Override
    public byte[] encode(E e) {
        return layout.doLayout((ILoggingEvent) e);
    }

    @Override
    public byte[] footerBytes() {
        return EMPTY_BYTES;
    }
}
