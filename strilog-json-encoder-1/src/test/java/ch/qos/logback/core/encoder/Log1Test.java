package ch.qos.logback.core.encoder;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class Log1Test {

    private static final Logger LOG = LoggerFactory.getLogger(Log1Test.class);

    @Test
    public void test() {
        LOG.info("Hello");
        LOG.info("argument {}", 1);
        MDC.put("key-1", "value-1");
        LOG.info("Logging with MDC {}", 2);
        LOG.error("Logging error {}", "error", new Exception());
        MDC.clear();
        LOG.warn("Null argument {} {} {}", null, 1, null);
        LOG.info("Large object {}", LOG);
    }
}
