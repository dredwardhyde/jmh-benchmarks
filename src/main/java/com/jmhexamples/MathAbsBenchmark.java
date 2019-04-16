package com.jmhexamples;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/*
    # JMH version: 1.21
    # VM version: JDK 1.8.0_201, Java HotSpot(TM) 64-Bit Server VM, 25.201-b09
    # VM options: -javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=54562:/Applications/IntelliJ IDEA CE.app/Contents/bin -Dfile.encoding=UTF-8
    # Warmup: 3 iterations, 5000 ms each
    # Measurement: 3 iterations, 5000 ms each
    # Timeout: 10 min per iteration
    # Threads: 1 thread, will synchronize iterations
    # Benchmark mode: Average time, time/op
    # Benchmark: com.jmhexamples.MathAbsBenchmark.testMethodCustom

    # Run progress: 0.00% complete, ETA 00:02:00
    # Warmup Fork: 1 of 1
    # Warmup Iteration   1: 9.040 ns/op
    # Warmup Iteration   2: 8.779 ns/op
    # Warmup Iteration   3: 8.298 ns/op
    Iteration   1: 8.340 ns/op
    Iteration   2: 8.203 ns/op
    Iteration   3: 8.391 ns/op

    # Run progress: 25.00% complete, ETA 00:01:32
    # Fork: 1 of 1
    # Warmup Iteration   1: 8.888 ns/op
    # Warmup Iteration   2: 8.820 ns/op
    # Warmup Iteration   3: 8.201 ns/op
    Iteration   1: 8.232 ns/op
    Iteration   2: 8.251 ns/op
    Iteration   3: 8.202 ns/op


    Result "com.jmhexamples.MathAbsBenchmark.testMethodCustom":
      8.229 ±(99.9%) 0.456 ns/op [Average]
      (min, avg, max) = (8.202, 8.229, 8.251), stdev = 0.025
      CI (99.9%): [7.773, 8.684] (assumes normal distribution)


    # JMH version: 1.21
    # VM version: JDK 1.8.0_201, Java HotSpot(TM) 64-Bit Server VM, 25.201-b09
    # VM options: -javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=54562:/Applications/IntelliJ IDEA CE.app/Contents/bin -Dfile.encoding=UTF-8
    # Warmup: 3 iterations, 5000 ms each
    # Measurement: 3 iterations, 5000 ms each
    # Timeout: 10 min per iteration
    # Threads: 1 thread, will synchronize iterations
    # Benchmark mode: Average time, time/op
    # Benchmark: com.jmhexamples.MathAbsBenchmark.testMethodMath

    # Run progress: 50.00% complete, ETA 00:01:01
    # Warmup Fork: 1 of 1
    # Warmup Iteration   1: 3.788 ns/op
    # Warmup Iteration   2: 3.793 ns/op
    # Warmup Iteration   3: 3.259 ns/op
    Iteration   1: 3.265 ns/op
    Iteration   2: 3.241 ns/op
    Iteration   3: 3.244 ns/op

    # Run progress: 75.00% complete, ETA 00:00:30
    # Fork: 1 of 1
    # Warmup Iteration   1: 3.813 ns/op
    # Warmup Iteration   2: 3.796 ns/op
    # Warmup Iteration   3: 3.249 ns/op
    Iteration   1: 3.355 ns/op
    Iteration   2: 3.499 ns/op
    Iteration   3: 3.369 ns/op


    Result "com.jmhexamples.MathAbsBenchmark.testMethodMath":
      3.408 ±(99.9%) 1.445 ns/op [Average]
      (min, avg, max) = (3.355, 3.408, 3.499), stdev = 0.079
      CI (99.9%): [1.963, 4.852] (assumes normal distribution)


    # Run complete. Total time: 00:02:02

    Benchmark                          Mode  Cnt  Score   Error  Units
    MathAbsBenchmark.testMethodCustom  avgt    3  8.229 ± 0.456  ns/op
    MathAbsBenchmark.testMethodMath    avgt    3  3.408 ± 1.445  ns/op
 */
public class MathAbsBenchmark {

    private static int absMath(int i) {
        return (i < 0) ? -i : i;
    }

    private static int absCustom(int i) {
        return (i >>> 31 == 1) ? ~i + 1 : i;
    }

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
