package com.jmhexamples;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import java.util.concurrent.TimeUnit;

public class SumIntegersBenchmark {

    public long sumAll(int n){
        long sum = 0;
        for(int i = 1; i <= n; i++){
            sum += i;
        }
        return sum;
    }

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(SumIntegersBenchmark.class.getSimpleName())
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
        blackhole.consume(sumAll( 200_000));
    }
}
