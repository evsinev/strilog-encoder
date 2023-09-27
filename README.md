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
    <version>1.0-2</version>
</dependency>
```

## Configure logback.xml

```xml
<appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
    <encoder class="com.payneteasy.strilog.encoder.json.JsonEncoder">
    </encoder>
</appender>
```
