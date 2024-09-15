package com.payneteasy.strilog.encoder.core;

import java.util.concurrent.atomic.AtomicLong;

public class CycleGenerator {

    private final AtomicLong cycle;

    public CycleGenerator() {
        cycle = new AtomicLong();
    }

    CycleGenerator(AtomicLong cycle) {
        this.cycle = cycle;
    }

    public long nextCycle() {
        long next = cycle.incrementAndGet();
        if (next < 0) {
            return Long.MIN_VALUE + next;
        }
        return next;
    }
}
