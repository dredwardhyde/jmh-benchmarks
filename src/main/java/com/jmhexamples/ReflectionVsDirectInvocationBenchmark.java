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
        # VM version: JDK 12.0.1-internal, OpenJDK 64-Bit Server VM, 12.0.1-internal+0-adhoc.edwardhyde.jdk12u

        Benchmark                                                               Mode  Cnt    Score   Error  Units
        ReflectionVsDirectInvocationBenchmark.testMethodDeclaredFullReflection  avgt    5  101.244 ± 2.617  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodDirect                  avgt    5    8.217 ± 0.032  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodFullReflection          avgt    5   96.477 ± 0.264  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodMethodHandle            avgt    5  832.951 ± 1.711  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodMethodHandleBind        avgt    5   11.001 ± 0.334  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodPartialReflection       avgt    5   12.327 ± 0.060  ns/op

        # JMH version: 1.21
        # VM version: JDK 12.0.2-internal, Eclipse OpenJ9 VM, master-5dd23af84
        Eclipse OpenJ9 OpenJDK 64-bit Server VM (12.0.2-internal+0-adhoc.edwardhyde.openj9-openjdk-jdk12)
        from bsd-x86_64 JRE with Extensions for OpenJDK for Eclipse OpenJ9 HEAD, built on Aug  8 2019 19:13:29
        by edwardhyde with Apple LLVM version 10.0.1 (clang-1001.0.46.4)

        Benchmark                                                               Mode  Cnt    Score    Error  Units
        ReflectionVsDirectInvocationBenchmark.testMethodDeclaredFullReflection  avgt    5  380.213 ± 10.493  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodDirect                  avgt    5    7.832 ±  0.032  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodFullReflection          avgt    5  343.269 ±  2.965  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodMethodHandle            avgt    5  264.036 ±  8.097  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodMethodHandleBind        avgt    5    9.533 ±  0.356  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodPartialReflection       avgt    5  146.070 ± 54.628  ns/op

        # JMH version: 1.21
        # VM version: JDK 12.0.2-internal, Eclipse OpenJ9 VM, master-5dd23af84
        # VM options: -Xshareclasses -Xaggressive

        Benchmark                                                               Mode  Cnt    Score    Error  Units
        ReflectionVsDirectInvocationBenchmark.testMethodDeclaredFullReflection  avgt    5  328.750 ± 10.925  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodDirect                  avgt    5    7.991 ±  0.028  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodFullReflection          avgt    5  337.580 ±  6.944  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodMethodHandle            avgt    5  274.214 ±  4.197  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodMethodHandleBind        avgt    5    9.738 ±  0.293  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodPartialReflection       avgt    5  141.923 ± 36.041  ns/op
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
