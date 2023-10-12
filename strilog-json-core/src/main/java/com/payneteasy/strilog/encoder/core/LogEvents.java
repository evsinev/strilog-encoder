package com.payneteasy.strilog.encoder.core;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LogEvents {

    private static final ZoneId            ZONE_ID        = ZoneId.systemDefault();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS Z");

    public static final byte[] EMPTY_BYTES = new byte[0];

    public static List<String> toList(Object[] aArguments) {
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

    public static LogEvent createLogEventWithException(Throwable e) {
        return new LogEvent()
                .setEpoch(System.currentTimeMillis())
                .setExceptionMessage(e.getMessage());
    }

    public static String formatDate(Instant aInstance) {
        return DATE_FORMATTER.format(aInstance.atZone(ZONE_ID));
    }

    public static Map<String, String> toMdc(Map<String, String> aMap) {
        return aMap != null && !aMap.isEmpty() ? aMap : null;
    }

}
