## Overview

Outputs log events as json

## Features

* small footprint (only 500kb)
* high performance
* zero allocation while creating json

## Include to pom.xml

```xml
<repositories>
    <repository>
        <id>pne</id>
        <name>payneteasy repo</name>
        <url>https://maven.pne.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.payneteasy.strilog-encoder</groupId>
    <artifactId>strilog-json-encoder-shaded</artifactId>
    <version>1.0-4</version>
</dependency>
```

## Configure logback.xml

```xml
<appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
    <encoder class="com.payneteasy.strilog.encoder.json.JsonEncoder">
    </encoder>
</appender>

<appender name="JSON_ONE_FILE" class="ch.qos.logback.core.FileAppender">
    <file>/var/log/example-app/logs/json.log</file>
    <append>true</append>
    <immediateFlush>false</immediateFlush>
    <encoder class="com.payneteasy.strilog.encoder.json.JsonEncoder">
    </encoder>
</appender>

<appender name="JSON" class="ch.qos.logback.core.rolling.RollingFileAppender">
    <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
      <fileNamePattern>/var/log/example-app/json/json-%d{yyyy-MM-dd-HH}.log</fileNamePattern>
      <maxHistory>30</maxHistory>
      <totalSizeCap>3GB</totalSizeCap>
    </rollingPolicy>
    <encoder class="com.payneteasy.strilog.encoder.json.JsonEncoder">
    </encoder>
</appender>

```
