package ch.qos.logback.core.encoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class ExampleApp {

    private static final Logger LOG = LoggerFactory.getLogger( ExampleApp.class );

    public static void main(String[] args) {
        LOG.info("Hello");
        LOG.info("argument {}", 1);
        MDC.put("key-1", "value-1");
        LOG.info("Logging with MDC {}", 2);
        LOG.error("Logging error {}", "error", new Exception());
        MDC.clear();
        LOG.warn("Null argument {} {} {}", null, 1, null);
        LOG.info("Large object {}", LOG);
        LOG.atInfo()
                .addKeyValue("key-1", "value-1")
                .log("Key value message");

//        for(int i=0; i<200; i++) {
//            new Thread(() -> {
//                for(int k=0; k<10_000; k++) {
//                    LOG.warn("Null argument {} {} {}", null, 1, null);
//                }
//            }).start();
//        }
    }
}
