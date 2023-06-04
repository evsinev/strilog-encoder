package com.payneteasy.strilog.encoder.json;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.dslplatform.json.DslJson;
import org.slf4j.event.KeyValuePair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.ZonedDateTime.now;

public class JsonLayout {

    private static final ZoneId ZONE_ID = ZoneId.systemDefault();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS Z");
    private final DslJson<LogEvent> dslJson = new DslJson<>();

    public byte[] doLayout(ILoggingEvent aEvent) {
        LogEvent log;

        try {
            ErrorInfo error = ErrorInfo.create(aEvent.getThrowableProxy());

            log = new LogEvent()
                    .setDate             ( formatter.format(aEvent.getInstant().atZone(ZONE_ID)))
                    .setClazz            ( aEvent.getLoggerName()            )
                    .setLevel            ( getLevel(aEvent)                  )
                    .setArgs             ( toList(aEvent.getArgumentArray()) )
                    .setEpoch            ( aEvent.getTimeStamp()             )
                    .setTemplate         ( aEvent.getMessage()               )
                    .setThread           ( aEvent.getThreadName()            )
                    .setMdc              ( aEvent.getMDCPropertyMap()        )
                    .setKv               ( toKv(aEvent.getKeyValuePairs())   )
                    .setStacktrace       ( error.getStackTrace()             )
                    .setExceptionLine    ( error.getExceptionLine()          )
                    .setExceptionMessage ( error.getExceptionMessage()       )
            ;
        } catch (Throwable e) {
            e.printStackTrace();
            log = new LogEvent()
                    .setEpoch(System.currentTimeMillis())
                    .setExceptionMessage(e.getMessage());
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(0x02);
            out.write('0');
            dslJson.serialize(log, out);
            out.write('\n');
            out.write(0x03);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    private Map<String, String> toKv(List<KeyValuePair> aPairs) {
        if(aPairs == null || aPairs.isEmpty()) {
            return null;
        }

        Map<String, String> kv = new HashMap<>();
        for (KeyValuePair pair : aPairs) {
            kv.put(pair.key, Objects.toString(pair.value));
        }
        return kv;
    }

    private String getLevel(ILoggingEvent aEvent) {
        return aEvent.getLevel() != null ? aEvent.getLevel().toString() : "DEBUG";
    }


    private List<String> toList(Object[] aArguments) {
        if (aArguments == null || aArguments.length == 0) {
            return null;
        }

        ArrayList<String> result = new ArrayList<>(aArguments.length);
        for (Object arg : aArguments) {
            if (arg != null) {
                result.add(arg.toString());
            } else {
                result.add(null);
            }
        }
        return result;
    }

}
