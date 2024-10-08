package com.payneteasy.strilog.encoder.json;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.payneteasy.strilog.encoder.core.CycleGenerator;
import com.payneteasy.strilog.encoder.core.ErrorInfo;
import com.payneteasy.strilog.encoder.core.LogEvent;
import com.payneteasy.strilog.encoder.core.LogEventEncoder;
import org.slf4j.event.KeyValuePair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.payneteasy.strilog.encoder.core.LogEvents.*;
import static com.payneteasy.strilog.encoder.json.ErrorInfos.createErrorInfo;

public class JsonLayout {

    private final LogEventEncoder encoder;
    private final String          appName;
    private final String          appInstance;
    private final String          hostname;
    private final CycleGenerator  cycleGenerator;

    public JsonLayout(byte[] stx, byte[] etx, String aAppName, String aAppInstance, String aHostname, CycleGenerator aCycleGenerator) {
        encoder        = new LogEventEncoder(stx, etx);
        appName        = aAppName;
        appInstance    = aAppInstance;
        hostname       = aHostname;
        cycleGenerator = aCycleGenerator;
    }

    public byte[] doLayout(ILoggingEvent aEvent) {
        LogEvent log;

        try {
            ErrorInfo error = createErrorInfo(aEvent.getThrowableProxy());

            log = new LogEvent()
                    .setDate             ( formatDate(aEvent.getInstant())   )
                    .setClazz            ( aEvent.getLoggerName()            )
                    .setLevel            ( getLevel(aEvent)                  )
                    .setArgs             ( toList(aEvent.getArgumentArray()) )
                    .setEpoch            ( aEvent.getTimeStamp()             )
                    .setTemplate         ( aEvent.getMessage()               )
                    .setThread           ( aEvent.getThreadName()            )
                    .setMdc              ( toMdc(aEvent.getMDCPropertyMap()) )
                    .setKv               ( toKv(aEvent.getKeyValuePairs())   )
                    .setStacktrace       ( error.getStackTrace()             )
                    .setExceptionLine    ( error.getExceptionLine()          )
                    .setExceptionMessage ( error.getExceptionMessage()       )
                    .setAppName          ( appName                           )
                    .setAppInstance      ( appInstance                       )
                    .setHostname         ( hostname                          )
                    .setCycle            ( cycleGenerator.nextCycle()        )
            ;
        } catch (Throwable e) {
            e.printStackTrace();
            log = createLogEventWithException(e);
        }

        return encoder.toBytesArray(log);
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

    public static String getLevel(ILoggingEvent aEvent) {
        return aEvent.getLevel() != null ? aEvent.getLevel().toString() : "DEBUG";
    }

}
