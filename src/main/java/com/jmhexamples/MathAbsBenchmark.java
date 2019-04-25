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
        # VM version: JDK 11.0.3, Java HotSpot(TM) 64-Bit Server VM, 11.0.3+12-LTS

        Benchmark                          Mode  Cnt  Score   Error  Units
        MathAbsBenchmark.testMethodCustom  avgt    3  8.567 ± 0.593  ns/op
        MathAbsBenchmark.testMethodMath    avgt    3  3.516 ± 0.036  ns/op
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
    @Warmup(iterations = 3, time = 5000, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 3, time = 5000, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    public void testMethodMath(Blackhole blackhole) {
        blackhole.consume(absMath(ThreadLocalRandom.current().nextInt()));
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 3, time = 5000, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 3, time = 5000, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    public void testMethodCustom(Blackhole blackhole) {
        blackhole.consume(absCustom(ThreadLocalRandom.current().nextInt()));
    }
}
