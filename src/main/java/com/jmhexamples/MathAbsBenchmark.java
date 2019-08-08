package com.jmhexamples;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class MathAbsBenchmark {

    private static int absMath(int i) {
        return (i < 0) ? -i : i;
    }

    private static int absCustom(int i) {
        return (i >>> 31 == 1) ? ~i + 1 : i;
    }

    /*
        # JMH version: 1.21
        # VM version: JDK 12.0.1-internal, OpenJDK 64-Bit Server VM, 12.0.1-internal+0-adhoc.edwardhyde.jdk12u
        Benchmark                          Mode  Cnt  Score   Error  Units
        MathAbsBenchmark.testMethodCustom  avgt    5  7.517 ± 0.402  ns/op
        MathAbsBenchmark.testMethodMath    avgt    5  2.933 ± 0.074  ns/op

        # JMH version: 1.21
        # VM version: JDK 12.0.2-internal, Eclipse OpenJ9 VM, master-5dd23af84
        Eclipse OpenJ9 OpenJDK 64-bit Server VM (12.0.2-internal+0-adhoc.edwardhyde.openj9-openjdk-jdk12)
        from bsd-x86_64 JRE with Extensions for OpenJDK for Eclipse OpenJ9 HEAD, built on Aug  8 2019 19:13:29
        by edwardhyde with Apple LLVM version 10.0.1 (clang-1001.0.46.4)
        Benchmark                          Mode  Cnt  Score    Error  Units
        MathAbsBenchmark.testMethodCustom  avgt    3  6.781 ± 11.746  ns/op
        MathAbsBenchmark.testMethodMath    avgt    3  6.465 ± 10.901  ns/op
     */
    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(MathAbsBenchmark.class.getSimpleName())
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
    public void testMethodMath(Blackhole blackhole) {
        blackhole.consume(absMath(ThreadLocalRandom.current().nextInt()));
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5, time = 20000, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 5, time = 20000, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    public void testMethodCustom(Blackhole blackhole) {
        blackhole.consume(absCustom(ThreadLocalRandom.current().nextInt()));
    }
}
