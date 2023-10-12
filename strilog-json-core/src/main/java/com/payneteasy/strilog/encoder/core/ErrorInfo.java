package com.payneteasy.strilog.encoder.core;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(makeFinal = true, level = PRIVATE)
@Builder
public class ErrorInfo {

    public static final ErrorInfo EMPTY = ErrorInfo.builder().build();

    String stackTrace;
    String exceptionLine;
    String exceptionMessage;

}
