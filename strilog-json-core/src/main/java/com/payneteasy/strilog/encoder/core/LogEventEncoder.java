package com.payneteasy.strilog.encoder.core;

import com.dslplatform.json.DslJson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class LogEventEncoder {

    private final DslJson<LogEvent> dslJson = new DslJson<>();

    private final byte[] stx;
    private final byte[] etx;

    public LogEventEncoder(byte[] stx, byte[] etx) {
        this.stx = stx;
        this.etx = etx;
    }

    public byte[] toBytesArray(LogEvent log) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            if (stx != null) {
                out.write(stx);
            }

            dslJson.serialize(log, out);

            out.write('\n');

            if (etx != null) {
                out.write(etx);
            }
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }

        return out.toByteArray();
    }


}
