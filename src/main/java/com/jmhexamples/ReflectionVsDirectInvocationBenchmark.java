package com.jmhexamples;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.lang.reflect.Method;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ReflectionVsDirectInvocationBenchmark {

    private static Random random = new Random();

    private static Method method = null;

    static {
        try {
            method = Random.class.getMethod("nextInt");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /*
        # JMH version: 1.21
        # VM version: JDK 1.8.0_201, Java HotSpot(TM) 64-Bit Server VM, 25.201-b09
        # VM options: -javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=49503:/Applications/IntelliJ IDEA CE.app/Contents/bin -Dfile.encoding=UTF-8

        Benchmark                                                               Mode  Cnt    Score   Error  Units
        ReflectionVsDirectInvocationBenchmark.testMethodDeclaredFullReflection  avgt    5  336.258 ± 6.670  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodDirect                  avgt    5    9.449 ± 0.031  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodFullReflection          avgt    5  150.757 ± 1.130  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodPartialReflection       avgt    5   11.017 ± 0.081  ns/op
     */
    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(ReflectionVsDirectInvocationBenchmark.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5, time = 10000, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 5, time = 10000, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    public void testMethodDirect(Blackhole blackhole) {
        blackhole.consume(random.nextInt());
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5, time = 10000, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 5, time = 10000, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    public void testMethodFullReflection(Blackhole blackhole) throws Exception {
        blackhole.consume(Random.class.getMethod("nextInt").invoke(random));
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5, time = 10000, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 5, time = 10000, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    public void testMethodDeclaredFullReflection(Blackhole blackhole) throws Exception {
        blackhole.consume(Random.class.getDeclaredMethod("nextInt").invoke(random));
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5, time = 10000, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 5, time = 10000, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    public void testMethodPartialReflection(Blackhole blackhole) throws Exception {
        blackhole.consume(method.invoke(random));
    }

}
