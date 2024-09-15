package com.payneteasy.strilog.encoder.json;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.payneteasy.strilog.encoder.core.CycleGenerator;
import com.payneteasy.strilog.encoder.core.ErrorInfo;
import com.payneteasy.strilog.encoder.core.LogEvent;
import com.payneteasy.strilog.encoder.core.LogEventEncoder;

import static com.payneteasy.strilog.encoder.core.LogEvents.*;
import static com.payneteasy.strilog.encoder.json.ErrorInfos.createErrorInfo;
import static java.time.Instant.ofEpochMilli;

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
                    .setDate     ( formatDate(ofEpochMilli(aEvent.getTimeStamp())) )
                    .setClazz    ( aEvent.getLoggerName()            )
                    .setLevel    ( getLevel(aEvent)                  )
                    .setArgs     ( toList(aEvent.getArgumentArray()) )
                    .setEpoch    ( aEvent.getTimeStamp()             )
                    .setTemplate ( aEvent.getMessage()               )
                    .setThread   ( aEvent.getThreadName()            )
                    .setMdc      ( toMdc(aEvent.getMDCPropertyMap()) )
//                    .setKv               (    )
                    .setStacktrace       ( error.getStackTrace()       )
                    .setExceptionLine    ( error.getExceptionLine()    )
                    .setExceptionMessage ( error.getExceptionMessage() )
                    .setAppName          ( appName                     )
                    .setAppInstance      ( appInstance                 )
                    .setHostname         ( hostname                    )
                    .setCycle            ( cycleGenerator.nextCycle()  )
            ;
        } catch (Throwable e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
            log = createLogEventWithException(e);
        }

        return encoder.toBytesArray(log);
    }

    public static String getLevel(ILoggingEvent aEvent) {
        return aEvent.getLevel() != null ? aEvent.getLevel().toString() : "DEBUG";
    }

}
