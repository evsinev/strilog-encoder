package com.payneteasy.strilog.encoder.json;

import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(makeFinal = true, level = PRIVATE)
@Builder
public class ErrorInfo {

    private static final ErrorInfo EMPTY = ErrorInfo.builder().build();

    String stackTrace;
    String exceptionLine;
    String exceptionMessage;

    public static ErrorInfo create(IThrowableProxy aProxy) {
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
