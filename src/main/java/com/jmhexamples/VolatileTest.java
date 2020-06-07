package com.jmhexamples;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/*
    # JMH version: 1.21
    # VM version: JDK 11.0.6, OpenJDK 64-Bit Server VM, 11.0.6+10-LTS
    # VM invoker: C:\Program Files\Zulu\zulu-11\bin\java.exe
    # VM options: -javaagent:C:\JetBrains\IntelliJ IDEA Community Edition 2019.3.3\lib\idea_rt.jar=60920:C:\JetBrains\IntelliJ IDEA Community Edition 2019.3.3\bin -Dfile.encoding=UTF-8 --module-path=C:\Users\edwardhyde\IdeaProjects\jmh-benchmarks\target\classes;C:\Users\edwardhyde\.m2\repository\org\openjdk\jmh\jmh-core\1.21\jmh-core-1.21.jar;C:\Users\edwardhyde\.m2\repository\net\sf\jopt-simple\jopt-simple\4.6\jopt-simple-4.6.jar;C:\Users\edwardhyde\.m2\repository\org\apache\commons\commons-math3\3.2\commons-math3-3.2.jar;C:\Users\edwardhyde\IdeaProjects\jmh-benchmarks\\unnamed.jar -Djdk.module.main=benchmarks
    # Warmup: 5 iterations, 20000 ms each
    # Measurement: 5 iterations, 20000 ms each
    # Timeout: 10 min per iteration
    # Threads: 1 thread, will synchronize iterations
    # Benchmark mode: Average time, time/op
    # Benchmark: com.jmhexamples.VolatileTest.nonVolatileRead

    # Run progress: 0,00% complete, ETA 00:26:40
    # Warmup Fork: 1 of 1
    # Warmup Iteration   1: 1,640 ns/op
    # Warmup Iteration   2: 1,731 ns/op
    # Warmup Iteration   3: 1,537 ns/op
    # Warmup Iteration   4: 1,538 ns/op
    # Warmup Iteration   5: 1,538 ns/op
    Iteration   1: 1,538 ns/op
    Iteration   2: 1,538 ns/op
    Iteration   3: 1,539 ns/op
    Iteration   4: 1,537 ns/op
    Iteration   5: 1,538 ns/op

    # Run progress: 12,50% complete, ETA 00:23:24
    # Fork: 1 of 1
    # Warmup Iteration   1: 1,622 ns/op
    # Warmup Iteration   2: 1,728 ns/op
    # Warmup Iteration   3: 1,532 ns/op
    # Warmup Iteration   4: 1,532 ns/op
    # Warmup Iteration   5: 1,533 ns/op
    Iteration   1: 1,533 ns/op
    Iteration   2: 1,533 ns/op
    Iteration   3: 1,532 ns/op
    Iteration   4: 1,532 ns/op
    Iteration   5: 1,534 ns/op


    Result "com.jmhexamples.VolatileTest.nonVolatileRead":
      1,533 ±(99.9%) 0,003 ns/op [Average]
      (min, avg, max) = (1,532, 1,533, 1,534), stdev = 0,001
      CI (99.9%): [1,530, 1,535] (assumes normal distribution)


    # JMH version: 1.21
    # VM version: JDK 11.0.6, OpenJDK 64-Bit Server VM, 11.0.6+10-LTS
    # VM invoker: C:\Program Files\Zulu\zulu-11\bin\java.exe
    # VM options: -javaagent:C:\JetBrains\IntelliJ IDEA Community Edition 2019.3.3\lib\idea_rt.jar=60920:C:\JetBrains\IntelliJ IDEA Community Edition 2019.3.3\bin -Dfile.encoding=UTF-8 --module-path=C:\Users\edwardhyde\IdeaProjects\jmh-benchmarks\target\classes;C:\Users\edwardhyde\.m2\repository\org\openjdk\jmh\jmh-core\1.21\jmh-core-1.21.jar;C:\Users\edwardhyde\.m2\repository\net\sf\jopt-simple\jopt-simple\4.6\jopt-simple-4.6.jar;C:\Users\edwardhyde\.m2\repository\org\apache\commons\commons-math3\3.2\commons-math3-3.2.jar;C:\Users\edwardhyde\IdeaProjects\jmh-benchmarks\\unnamed.jar -Djdk.module.main=benchmarks
    # Warmup: 5 iterations, 20000 ms each
    # Measurement: 5 iterations, 20000 ms each
    # Timeout: 10 min per iteration
    # Threads: 1 thread, will synchronize iterations
    # Benchmark mode: Average time, time/op
    # Benchmark: com.jmhexamples.VolatileTest.nonVolatileWrite

    # Run progress: 25,00% complete, ETA 00:20:03
    # Warmup Fork: 1 of 1
    # Warmup Iteration   1: 7,562 ns/op
    # Warmup Iteration   2: 7,570 ns/op
    # Warmup Iteration   3: 7,558 ns/op
    # Warmup Iteration   4: 7,559 ns/op
    # Warmup Iteration   5: 7,557 ns/op
    Iteration   1: 7,555 ns/op
    Iteration   2: 7,575 ns/op
    Iteration   3: 7,553 ns/op
    Iteration   4: 7,551 ns/op
    Iteration   5: 7,553 ns/op

    # Run progress: 37,50% complete, ETA 00:16:43
    # Fork: 1 of 1
    # Warmup Iteration   1: 7,556 ns/op
    # Warmup Iteration   2: 7,567 ns/op
    # Warmup Iteration   3: 7,556 ns/op
    # Warmup Iteration   4: 7,554 ns/op
    # Warmup Iteration   5: 7,554 ns/op
    Iteration   1: 7,554 ns/op
    Iteration   2: 7,550 ns/op
    Iteration   3: 7,553 ns/op
    Iteration   4: 7,551 ns/op
    Iteration   5: 7,552 ns/op


    Result "com.jmhexamples.VolatileTest.nonVolatileWrite":
      7,552 ±(99.9%) 0,006 ns/op [Average]
      (min, avg, max) = (7,550, 7,552, 7,554), stdev = 0,002
      CI (99.9%): [7,546, 7,558] (assumes normal distribution)


    # JMH version: 1.21
    # VM version: JDK 11.0.6, OpenJDK 64-Bit Server VM, 11.0.6+10-LTS
    # VM invoker: C:\Program Files\Zulu\zulu-11\bin\java.exe
    # VM options: -javaagent:C:\JetBrains\IntelliJ IDEA Community Edition 2019.3.3\lib\idea_rt.jar=60920:C:\JetBrains\IntelliJ IDEA Community Edition 2019.3.3\bin -Dfile.encoding=UTF-8 --module-path=C:\Users\edwardhyde\IdeaProjects\jmh-benchmarks\target\classes;C:\Users\edwardhyde\.m2\repository\org\openjdk\jmh\jmh-core\1.21\jmh-core-1.21.jar;C:\Users\edwardhyde\.m2\repository\net\sf\jopt-simple\jopt-simple\4.6\jopt-simple-4.6.jar;C:\Users\edwardhyde\.m2\repository\org\apache\commons\commons-math3\3.2\commons-math3-3.2.jar;C:\Users\edwardhyde\IdeaProjects\jmh-benchmarks\\unnamed.jar -Djdk.module.main=benchmarks
    # Warmup: 5 iterations, 20000 ms each
    # Measurement: 5 iterations, 20000 ms each
    # Timeout: 10 min per iteration
    # Threads: 1 thread, will synchronize iterations
    # Benchmark mode: Average time, time/op
    # Benchmark: com.jmhexamples.VolatileTest.volatileRead

    # Run progress: 50,00% complete, ETA 00:13:22
    # Warmup Fork: 1 of 1
    # Warmup Iteration   1: 1,621 ns/op
    # Warmup Iteration   2: 1,729 ns/op
    # Warmup Iteration   3: 1,533 ns/op
    # Warmup Iteration   4: 1,532 ns/op
    # Warmup Iteration   5: 1,534 ns/op
    Iteration   1: 1,532 ns/op
    Iteration   2: 1,531 ns/op
    Iteration   3: 1,533 ns/op
    Iteration   4: 1,535 ns/op
    Iteration   5: 1,533 ns/op

    # Run progress: 62,50% complete, ETA 00:10:01
    # Fork: 1 of 1
    # Warmup Iteration   1: 1,621 ns/op
    # Warmup Iteration   2: 1,727 ns/op
    # Warmup Iteration   3: 1,533 ns/op
    # Warmup Iteration   4: 1,533 ns/op
    # Warmup Iteration   5: 1,532 ns/op
    Iteration   1: 1,532 ns/op
    Iteration   2: 1,535 ns/op
    Iteration   3: 1,532 ns/op
    Iteration   4: 1,535 ns/op
    Iteration   5: 1,531 ns/op


    Result "com.jmhexamples.VolatileTest.volatileRead":
      1,533 ±(99.9%) 0,007 ns/op [Average]
      (min, avg, max) = (1,531, 1,533, 1,535), stdev = 0,002
      CI (99.9%): [1,526, 1,540] (assumes normal distribution)


    # JMH version: 1.21
    # VM version: JDK 11.0.6, OpenJDK 64-Bit Server VM, 11.0.6+10-LTS
    # VM invoker: C:\Program Files\Zulu\zulu-11\bin\java.exe
    # VM options: -javaagent:C:\JetBrains\IntelliJ IDEA Community Edition 2019.3.3\lib\idea_rt.jar=60920:C:\JetBrains\IntelliJ IDEA Community Edition 2019.3.3\bin -Dfile.encoding=UTF-8 --module-path=C:\Users\edwardhyde\IdeaProjects\jmh-benchmarks\target\classes;C:\Users\edwardhyde\.m2\repository\org\openjdk\jmh\jmh-core\1.21\jmh-core-1.21.jar;C:\Users\edwardhyde\.m2\repository\net\sf\jopt-simple\jopt-simple\4.6\jopt-simple-4.6.jar;C:\Users\edwardhyde\.m2\repository\org\apache\commons\commons-math3\3.2\commons-math3-3.2.jar;C:\Users\edwardhyde\IdeaProjects\jmh-benchmarks\\unnamed.jar -Djdk.module.main=benchmarks
    # Warmup: 5 iterations, 20000 ms each
    # Measurement: 5 iterations, 20000 ms each
    # Timeout: 10 min per iteration
    # Threads: 1 thread, will synchronize iterations
    # Benchmark mode: Average time, time/op
    # Benchmark: com.jmhexamples.VolatileTest.volatileWrite

    # Run progress: 75,00% complete, ETA 00:06:41
    # Warmup Fork: 1 of 1
    # Warmup Iteration   1: 10,754 ns/op
    # Warmup Iteration   2: 9,709 ns/op
    # Warmup Iteration   3: 11,004 ns/op
    # Warmup Iteration   4: 11,005 ns/op
    # Warmup Iteration   5: 11,003 ns/op
    Iteration   1: 11,006 ns/op
    Iteration   2: 11,003 ns/op
    Iteration   3: 11,005 ns/op
    Iteration   4: 11,006 ns/op
    Iteration   5: 11,008 ns/op

    # Run progress: 87,50% complete, ETA 00:03:20
    # Fork: 1 of 1
    # Warmup Iteration   1: 9,547 ns/op
    # Warmup Iteration   2: 9,723 ns/op
    # Warmup Iteration   3: 9,504 ns/op
    # Warmup Iteration   4: 9,491 ns/op
    # Warmup Iteration   5: 9,500 ns/op
    Iteration   1: 9,498 ns/op
    Iteration   2: 9,494 ns/op
    Iteration   3: 9,490 ns/op
    Iteration   4: 9,485 ns/op
    Iteration   5: 9,490 ns/op


    Result "com.jmhexamples.VolatileTest.volatileWrite":
      9,491 ±(99.9%) 0,019 ns/op [Average]
      (min, avg, max) = (9,485, 9,491, 9,498), stdev = 0,005
      CI (99.9%): [9,473, 9,510] (assumes normal distribution)


    # Run complete. Total time: 00:26:44

    REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
    why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
    experiments, perform baseline and negative tests that provide experimental control, make sure
    the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
    Do not assume the numbers tell you what you want them to tell.

    Benchmark                      Mode  Cnt  Score   Error  Units
    VolatileTest.nonVolatileRead   avgt    5  1,533 ± 0,003  ns/op
    VolatileTest.nonVolatileWrite  avgt    5  7,552 ± 0,006  ns/op
    VolatileTest.volatileRead      avgt    5  1,533 ± 0,007  ns/op
    VolatileTest.volatileWrite     avgt    5  9,491 ± 0,019  ns/op

    Process finished with exit code 0

 */
@State(Scope.Benchmark)
public class VolatileTest {

    public static final Random random = new Random();

    public int rand1 = random.nextInt();
    public volatile int rand2 = random.nextInt();

    public int rand3;
    public volatile int rand4;

    public static final VolatileTest volatileTest = new VolatileTest();

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(VolatileTest.class.getSimpleName())
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
        blackhole.consume(volatileTest.rand1);
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5, time = 20000, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 5, time = 20000, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    public void volatileRead(Blackhole blackhole) {
        blackhole.consume(volatileTest.rand2);
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5, time = 20000, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 5, time = 20000, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    public void nonVolatileWrite(Blackhole blackhole) {
        volatileTest.rand3 = random.nextInt();
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5, time = 20000, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 5, time = 20000, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    public void volatileWrite(Blackhole blackhole) {
        volatileTest.rand4 = random.nextInt();
    }
}
