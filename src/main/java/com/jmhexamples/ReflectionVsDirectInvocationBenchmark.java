package com.jmhexamples;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ReflectionVsDirectInvocationBenchmark {

    private static Random random = new Random();

    private static Method method = null;

    private static MethodHandles.Lookup lookup = MethodHandles.publicLookup();

    private static MethodHandle methodHandle = null;

    static {
        try {
            methodHandle = lookup.findVirtual(Random.class, "nextInt", MethodType.methodType(int.class)).bindTo(random);
            method = Random.class.getMethod("nextInt");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /*
        # JMH version: 1.21
        # VM version: JDK 11.0.3, Java HotSpot(TM) 64-Bit Server VM, 11.0.3+12-LTS

        # Method is known at compile-time or some preparations were made
        ReflectionVsDirectInvocationBenchmark.testMethodDirect                  avgt    5    9.404 ± 0.055  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodMethodHandleBind        avgt    5   11.074 ± 0.067  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodPartialReflection       avgt    5   14.772 ± 0.050  ns/op

        # Method is determined at runtime
        ReflectionVsDirectInvocationBenchmark.testMethodFullReflection          avgt    5  111.641 ± 9.978  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodDeclaredFullReflection  avgt    5  117.025 ± 0.660  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodMethodHandle            avgt    5  896.651 ± 7.286  ns/op
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

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5, time = 10000, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 5, time = 10000, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    public void testMethodMethodHandle(Blackhole blackhole) throws Throwable {
        blackhole.consume((int) lookup.findVirtual(Random.class, "nextInt", MethodType.methodType(int.class)).invokeExact(random));
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5, time = 10000, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 5, time = 10000, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    public void testMethodMethodHandleBind(Blackhole blackhole) throws Throwable {
        blackhole.consume((int) methodHandle.invokeExact());
    }
}
