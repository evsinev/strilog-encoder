package com.payneteasy.strilog.encoder.json;

import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import com.payneteasy.strilog.encoder.core.ErrorInfo;

import static com.payneteasy.strilog.encoder.core.ErrorInfo.EMPTY;

public class ErrorInfos {

    public static ErrorInfo createErrorInfo(IThrowableProxy aProxy) {
        if(aProxy == null) {
            return EMPTY;
        }

        return ErrorInfo.builder()
                .stackTrace         ( ThrowableProxyUtil.asString(aProxy) )
                .exceptionLine      ( getExceptionLine(aProxy)            )
                .exceptionMessage   ( aProxy.getMessage()                 )
                .build();
    }

    private static String getExceptionLine(IThrowableProxy tp) {
        StackTraceElementProxy[] elements = tp.getStackTraceElementProxyArray();
        if (elements.length > 0) {
            return elements[0].toString();
        } else {
            return null;
        }
    }

}
