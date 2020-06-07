package com.jmhexamples;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class UUIDGenerationBenchmark {

    /*
    # JMH version: 1.21
    # VM version: JDK 11.0.4, OpenJDK 64-Bit Server VM, 11.0.4+11-LTS

    Result "com.jmhexamples.UUIDGenerationBenchmark.nonVolatileRead":
    647.198 ±(99.9%) 141.296 ns/op [Average]
    (min, avg, max) = (616.627, 647.198, 707.965), stdev = 36.694
    CI (99.9%): [505.902, 788.494] (assumes normal distribution)

    Benchmark                                Mode  Cnt    Score     Error  Units
    UUIDGenerationBenchmark.nonVolatileRead  avgt    5  647.198 ± 141.296  ns/op

     */
    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(UUIDGenerationBenchmark.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5, time = 20000, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 5, time = 20000, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    public void nonVolatileRead(Blackhole blackhole) {
        blackhole.consume(UUID.randomUUID());
    }

}
