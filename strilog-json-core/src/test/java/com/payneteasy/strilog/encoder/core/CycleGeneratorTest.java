package com.payneteasy.strilog.encoder.core;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.assertEquals;

public class CycleGeneratorTest {

    @Test
    public void test() {
        CycleGenerator generator = new CycleGenerator(new AtomicLong(Long.MAX_VALUE));
        assertEquals(0, generator.nextCycle());
        assertEquals(1, generator.nextCycle());
    }
}