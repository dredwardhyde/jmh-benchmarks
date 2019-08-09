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

        -------------------------------------------JDK 12--------------------------------------------------------

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

        # JMH version: 1.21
        # VM version: JDK 12.0.2-internal, Eclipse OpenJ9 VM, master-5dd23af84
        # VM options:   -Xshareclasses
                        -Xaggressive
                        -Xjit:enableJITServerHeuristics
                        -Xjit:enableOSR
                        -Xjit:enableExpensiveOptsAtWarm
                        -Xjit:enableEarlyCompilationDuringIdleCpu
                        -Xjit:enableCheapWarmOpts
                        -Xjit:enableAOTInlineSystemMethod
                        -Xjit:x86HLE
                        -Xjit:enableRecompilationPushing
        Benchmark                                                               Mode  Cnt    Score    Error  Units
        ReflectionVsDirectInvocationBenchmark.testMethodDeclaredFullReflection  avgt    5  394.726 ± 18.615  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodDirect                  avgt    5    9.355 ±  0.023  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodFullReflection          avgt    5  386.583 ±  1.554  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodMethodHandle            avgt    5  331.513 ±  0.799  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodMethodHandleBind        avgt    5   11.409 ±  0.276  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodPartialReflection       avgt    5  174.455 ± 62.027  ns/op


        # JMH version: 1.21
        # VM version: JDK 12.0.2-internal, Eclipse OpenJ9 VM, master-5dd23af84
        # VM options:   -Xshareclasses
                        -Xjit:enableJITServerHeuristics
                        -Xjit:enableOSR
                        -Xjit:enableExpensiveOptsAtWarm
                        -Xjit:enableEarlyCompilationDuringIdleCpu
                        -Xjit:enableCheapWarmOpts
                        -Xjit:enableAOTInlineSystemMethod
                        -Xjit:x86HLE
                        -Xjit:enableRecompilationPushing

        Benchmark                                                               Mode  Cnt    Score    Error  Units
        ReflectionVsDirectInvocationBenchmark.testMethodDeclaredFullReflection  avgt    5  420.185 ± 32.827  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodDirect                  avgt    5    9.387 ±  0.014  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodFullReflection          avgt    5  419.222 ± 16.032  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodMethodHandle            avgt    5  325.031 ±  3.315  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodMethodHandleBind        avgt    5   11.400 ±  0.432  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodPartialReflection       avgt    5  167.134 ± 48.873  ns/op


        -------------------------------------------JDK 8----------------------------------------------------------

        # JMH version: 1.21
        # VM version: JDK 1.8.0_222, OpenJDK 64-Bit GraalVM CE 19.1.1, 25.222-b08-jvmci-19.1-b01

        Benchmark                                                               Mode  Cnt    Score    Error  Units
        ReflectionVsDirectInvocationBenchmark.testMethodDeclaredFullReflection  avgt    5  401.587 ± 49.821  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodDirect                  avgt    5   11.766 ±  0.022  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodFullReflection          avgt    5  173.754 ±  1.749  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodMethodHandle            avgt    5  845.309 ±  6.114  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodMethodHandleBind        avgt    5   12.038 ±  0.049  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodPartialReflection       avgt    5   13.145 ±  0.051  ns/op


        # JMH version: 1.21
        # VM version: JDK 1.8.0_222, OpenJDK 64-Bit Server VM, 25.222-b10

        Benchmark                                                               Mode  Cnt    Score   Error  Units
        ReflectionVsDirectInvocationBenchmark.testMethodDeclaredFullReflection  avgt    5  342.100 ± 7.047  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodDirect                  avgt    5    9.907 ± 0.052  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodFullReflection          avgt    5  147.142 ± 1.862  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodMethodHandle            avgt    5  637.023 ± 4.955  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodMethodHandleBind        avgt    5   13.423 ± 1.261  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodPartialReflection       avgt    5   12.085 ± 0.654  ns/op


        # JMH version: 1.21
        # VM version: JDK 1.8.0_222, Eclipse OpenJ9 VM, openj9-0.15.1

        Benchmark                                                               Mode  Cnt   Score   Error  Units
        ReflectionVsDirectInvocationBenchmark.testMethodDeclaredFullReflection  avgt    5  74.017 ± 8.299  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodDirect                  avgt    5   9.384 ± 0.017  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodFullReflection          avgt    5  68.772 ± 8.543  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodMethodHandle            avgt    5  88.150 ± 1.486  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodMethodHandleBind        avgt    5  12.839 ± 0.059  ns/op
        ReflectionVsDirectInvocationBenchmark.testMethodPartialReflection       avgt    5  14.794 ± 1.407  ns/op
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
