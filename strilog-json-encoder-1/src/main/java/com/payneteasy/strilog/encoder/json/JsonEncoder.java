package com.payneteasy.strilog.encoder.json;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.encoder.EncoderBase;
import ch.qos.logback.core.spi.PropertyDefiner;
import com.payneteasy.strilog.encoder.core.CycleGenerator;

import static com.payneteasy.strilog.encoder.core.Hostnames.getHostname;
import static com.payneteasy.strilog.encoder.core.LogEvents.EMPTY_BYTES;

public class JsonEncoder<E> extends EncoderBase<E> {

    private JsonLayout       layout;
    private PropertyDefiner  versionProvider;

    public void setVersionProvider(PropertyDefiner aVersionProvider) {
        versionProvider = aVersionProvider;
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
    public void start() {
        String version = versionProvider != null ? versionProvider.getPropertyValue() : null;

        layout = new JsonLayout(
                null
                , null
                , context.getProperty("app-name")
                , context.getProperty("app-instance")
                , version
                , getHostname()
                , new CycleGenerator()
        );
        super.start();
    }

    @Override
    public byte[] footerBytes() {
        return EMPTY_BYTES;
    }
}
