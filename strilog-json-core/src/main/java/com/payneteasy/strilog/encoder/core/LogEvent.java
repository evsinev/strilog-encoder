package com.payneteasy.strilog.encoder.core;

import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.JsonAttribute;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@CompiledJson(objectFormatPolicy = CompiledJson.ObjectFormatPolicy.MINIMAL)
@Accessors(chain = true)
@Setter
public class LogEvent {

    @JsonAttribute(index = 0)
    String date;

    @JsonAttribute(index = 1)
    long epoch;

    @JsonAttribute(index = 2)
    String level;

    @JsonAttribute(index = 3)
    String thread;

    @JsonAttribute(index = 4)
    String clazz;

    @JsonAttribute(index = 5)
    Map<String, String> mdc;

    @JsonAttribute(index = 6)
    String template;

    @JsonAttribute(index = 7)
    List<String> args;

    @JsonAttribute(index = 8)
    Map<String, String> kv;

    @JsonAttribute(index = 9, name = "message_id")
    String messageId;

    @JsonAttribute(index = 10)
    String stacktrace;

    @JsonAttribute(index = 11, name = "exception_line")
    String exceptionLine;

    @JsonAttribute(index = 12, name = "exception_message")
    String exceptionMessage;

    @JsonAttribute(index = 13, name = "app_name")
    String appName;

    @JsonAttribute(index = 14, name = "app_instance")
    String appInstance;
}
