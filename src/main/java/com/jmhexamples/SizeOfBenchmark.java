package com.jmhexamples;

import com.company.ObjectSizeFetcher;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class SizeOfBenchmark {

    private static Unsafe unsafe = null;

    private static HashMap hashMap = new HashMap<>();

    static {
        try {
            Constructor<Unsafe> unsafeConstructor = Unsafe.class.getDeclaredConstructor();
            unsafeConstructor.setAccessible(true);
            unsafe = unsafeConstructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        # JMH version: 1.21
        # VM version: JDK 12.0.1-internal, OpenJDK 64-Bit Server VM, 12.0.1-internal+0-adhoc.edwardhyde.jdk12u
        # VM options: -javaagent:/Users/edwardhyde/Documents/Development/jmh-examples/benchmarks/unnamed.jar

        Benchmark                         Mode  Cnt     Score   Error  Units
        SizeOfBenchmark.testMethodAgent   avgt    5   114.608 ± 2.342  ns/op
        SizeOfBenchmark.testMethodUnsafe  avgt    5  1315.649 ± 1.887  ns/op
     */
    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(SizeOfBenchmark.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }

    // not working for array objects / gives different result from Java agent
    private static long sizeOfUnsafe(Object object) {
        Class<?> clazz = object.getClass();
        long maximumOffset = 0;
        do {
            for (Field f : clazz.getDeclaredFields()) {
                if (!Modifier.isStatic(f.getModifiers())) {
                    maximumOffset = Math.max(maximumOffset, unsafe.objectFieldOffset(f));
                }
            }
        } while ((clazz = clazz.getSuperclass()) != null);
        return maximumOffset + 8;
    }


    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5, time = 20000, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 5, time = 20000, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    public void testMethodUnsafe(Blackhole blackhole) {
        blackhole.consume(sizeOfUnsafe(hashMap));
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5, time = 20000, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 5, time = 20000, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    public void testMethodAgent(Blackhole blackhole) {
        blackhole.consume(ObjectSizeFetcher.getObjectSize(hashMap));
    }

}
