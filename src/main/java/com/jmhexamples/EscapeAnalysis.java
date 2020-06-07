package com.jmhexamples;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class EscapeAnalysis {

    public static final int SIZE = 100_000;

    public static final class Vector2D {
        private double x;
        private double y;

        public Vector2D(final double x,
                        final double y) {
            this.x = x;
            this.y = y;
        }

        public Vector2D add(final Vector2D other) {
            return new Vector2D(
                    x + other.x,
                    y + other.y
            );
        }

        public void addAccumulate(final Vector2D other) {
            x = x + other.x;
            y = y + other.y;
        }

        public static Vector2D randomVector(final ThreadLocalRandom rnd) {
            return new Vector2D(rnd.nextDouble(), rnd.nextDouble());
        }

        public double dot(final Vector2D other) {
            return x * other.x + y * other.y;
        }

        public double length() {
            return Math.sqrt(this.dot(this));
        }
    }


    /*
    # JMH version: 1.21
    # VM version: JDK 14.0.1, OpenJDK 64-Bit Server VM, 14.0.1+8
    # VM invoker: C:\Java\BellSoft\LibericaJDK-14\bin\java.exe
    # VM options: -javaagent:C:\JetBrains\IntelliJ IDEA Community Edition 2019.3.3\lib\idea_rt.jar=55351:C:\JetBrains\IntelliJ IDEA Community Edition 2019.3.3\bin -Dfile.encoding=UTF-8 --module-path=C:\Users\edwardhyde\IdeaProjects\jmh-benchmarks\target\classes;C:\Users\edwardhyde\.m2\repository\org\openjdk\jmh\jmh-core\1.21\jmh-core-1.21.jar;C:\Users\edwardhyde\.m2\repository\net\sf\jopt-simple\jopt-simple\4.6\jopt-simple-4.6.jar;C:\Users\edwardhyde\.m2\repository\org\apache\commons\commons-math3\3.2\commons-math3-3.2.jar;C:\Users\edwardhyde\IdeaProjects\jmh-benchmarks\\unnamed.jar -Djdk.module.main=benchmarks
    # Warmup: 5 iterations, 20000 ms each
    # Measurement: 5 iterations, 20000 ms each
    # Timeout: 10 min per iteration
    # Threads: 1 thread, will synchronize iterations
    # Benchmark mode: Average time, time/op
    # Benchmark: com.jmhexamples.EscapeAnalysis.bad

    # Run progress: 0,00% complete, ETA 00:13:20
    # Warmup Fork: 1 of 1
    # Warmup Iteration   1: 377009,321 ns/op
    # Warmup Iteration   2: 380054,045 ns/op
    # Warmup Iteration   3: 385613,251 ns/op
    # Warmup Iteration   4: 377909,344 ns/op
    # Warmup Iteration   5: 369715,282 ns/op
    Iteration   1: 368384,863 ns/op
                     ·gc.alloc.rate:                   8081,524 MB/sec
                     ·gc.alloc.rate.norm:              3200098,210 B/op
                     ·gc.churn.G1_Eden_Space:          8064,779 MB/sec
                     ·gc.churn.G1_Eden_Space.norm:     3193467,794 B/op
                     ·gc.churn.G1_Survivor_Space:      0,006 MB/sec
                     ·gc.churn.G1_Survivor_Space.norm: 2,519 B/op
                     ·gc.count:                        156,000 counts
                     ·gc.time:                         139,000 ms

    Iteration   2: 369514,750 ns/op
                     ·gc.alloc.rate:                   8057,204 MB/sec
                     ·gc.alloc.rate.norm:              3200098,410 B/op
                     ·gc.churn.G1_Eden_Space:          8065,465 MB/sec
                     ·gc.churn.G1_Eden_Space.norm:     3203379,533 B/op
                     ·gc.churn.G1_Survivor_Space:      0,005 MB/sec
                     ·gc.churn.G1_Survivor_Space.norm: 1,830 B/op
                     ·gc.count:                        156,000 counts
                     ·gc.time:                         137,000 ms

    Iteration   3: 370904,106 ns/op
                     ·gc.alloc.rate:                   8026,087 MB/sec
                     ·gc.alloc.rate.norm:              3200098,237 B/op
                     ·gc.churn.G1_Eden_Space:          8012,866 MB/sec
                     ·gc.churn.G1_Eden_Space.norm:     3194826,830 B/op
                     ·gc.churn.G1_Survivor_Space:      0,006 MB/sec
                     ·gc.churn.G1_Survivor_Space.norm: 2,215 B/op
                     ·gc.count:                        155,000 counts
                     ·gc.time:                         133,000 ms

    Iteration   4: 370215,939 ns/op
                     ·gc.alloc.rate:                   8042,109 MB/sec
                     ·gc.alloc.rate.norm:              3200097,731 B/op
                     ·gc.churn.G1_Eden_Space:          8065,555 MB/sec
                     ·gc.churn.G1_Eden_Space.norm:     3209427,449 B/op
                     ·gc.churn.G1_Survivor_Space:      0,007 MB/sec
                     ·gc.churn.G1_Survivor_Space.norm: 2,804 B/op
                     ·gc.count:                        156,000 counts
                     ·gc.time:                         141,000 ms

    Iteration   5: 368808,373 ns/op
                     ·gc.alloc.rate:                   8072,372 MB/sec
                     ·gc.alloc.rate.norm:              3200097,131 B/op
                     ·gc.churn.G1_Eden_Space:          8064,709 MB/sec
                     ·gc.churn.G1_Eden_Space.norm:     3197059,599 B/op
                     ·gc.churn.G1_Survivor_Space:      0,005 MB/sec
                     ·gc.churn.G1_Survivor_Space.norm: 2,032 B/op
                     ·gc.count:                        156,000 counts
                     ·gc.time:                         138,000 ms


    # Run progress: 25,00% complete, ETA 00:10:17
    # Fork: 1 of 1
    # Warmup Iteration   1: 375820,693 ns/op
    # Warmup Iteration   2: 380584,623 ns/op
    # Warmup Iteration   3: 385877,558 ns/op
    # Warmup Iteration   4: 374561,490 ns/op
    # Warmup Iteration   5: 360557,677 ns/op
    Iteration   1: 361091,611 ns/op
                     ·gc.alloc.rate:                   8245,168 MB/sec
                     ·gc.alloc.rate.norm:              3200098,566 B/op
                     ·gc.churn.G1_Eden_Space:          8272,233 MB/sec
                     ·gc.churn.G1_Eden_Space.norm:     3210602,618 B/op
                     ·gc.churn.G1_Survivor_Space:      0,006 MB/sec
                     ·gc.churn.G1_Survivor_Space.norm: 2,469 B/op
                     ·gc.count:                        160,000 counts
                     ·gc.time:                         147,000 ms

    Iteration   2: 360952,772 ns/op
                     ·gc.alloc.rate:                   8248,295 MB/sec
                     ·gc.alloc.rate.norm:              3200098,123 B/op
                     ·gc.churn.G1_Eden_Space:          8220,533 MB/sec
                     ·gc.churn.G1_Eden_Space.norm:     3189327,204 B/op
                     ·gc.churn.G1_Survivor_Space:      0,005 MB/sec
                     ·gc.churn.G1_Survivor_Space.norm: 2,128 B/op
                     ·gc.count:                        159,000 counts
                     ·gc.time:                         145,000 ms

    Iteration   3: 360887,094 ns/op
                     ·gc.alloc.rate:                   8249,399 MB/sec
                     ·gc.alloc.rate.norm:              3200098,526 B/op
                     ·gc.churn.G1_Eden_Space:          8271,847 MB/sec
                     ·gc.churn.G1_Eden_Space.norm:     3208806,784 B/op
                     ·gc.churn.G1_Survivor_Space:      0,006 MB/sec
                     ·gc.churn.G1_Survivor_Space.norm: 2,411 B/op
                     ·gc.count:                        160,000 counts
                     ·gc.time:                         147,000 ms

    Iteration   4: 360599,328 ns/op
                     ·gc.alloc.rate:                   8256,445 MB/sec
                     ·gc.alloc.rate.norm:              3200097,531 B/op
                     ·gc.churn.G1_Eden_Space:          8272,198 MB/sec
                     ·gc.churn.G1_Eden_Space.norm:     3206203,501 B/op
                     ·gc.churn.G1_Survivor_Space:      0,007 MB/sec
                     ·gc.churn.G1_Survivor_Space.norm: 2,679 B/op
                     ·gc.count:                        160,000 counts
                     ·gc.time:                         146,000 ms

    Iteration   5: 360582,623 ns/op
                     ·gc.alloc.rate:                   8256,749 MB/sec
                     ·gc.alloc.rate.norm:              3200096,906 B/op
                     ·gc.churn.G1_Eden_Space:          8220,357 MB/sec
                     ·gc.churn.G1_Eden_Space.norm:     3185992,411 B/op
                     ·gc.churn.G1_Survivor_Space:      0,006 MB/sec
                     ·gc.churn.G1_Survivor_Space.norm: 2,391 B/op
                     ·gc.count:                        159,000 counts
                     ·gc.time:                         142,000 ms



    Result "com.jmhexamples.EscapeAnalysis.bad":
      360822,685 ±(99.9%) 862,988 ns/op [Average]
      (min, avg, max) = (360582,623, 360822,685, 361091,611), stdev = 224,115
      CI (99.9%): [359959,698, 361685,673] (assumes normal distribution)

    Secondary result "com.jmhexamples.EscapeAnalysis.bad:·gc.alloc.rate":
      8251,211 ±(99.9%) 19,855 MB/sec [Average]
      (min, avg, max) = (8245,168, 8251,211, 8256,749), stdev = 5,156
      CI (99.9%): [8231,356, 8271,066] (assumes normal distribution)

    Secondary result "com.jmhexamples.EscapeAnalysis.bad:·gc.alloc.rate.norm":
      3200097,931 ±(99.9%) 2,725 B/op [Average]
      (min, avg, max) = (3200096,906, 3200097,931, 3200098,566), stdev = 0,708
      CI (99.9%): [3200095,205, 3200100,656] (assumes normal distribution)

    Secondary result "com.jmhexamples.EscapeAnalysis.bad:·gc.churn.G1_Eden_Space":
      8251,434 ±(99.9%) 108,932 MB/sec [Average]
      (min, avg, max) = (8220,357, 8251,434, 8272,233), stdev = 28,289
      CI (99.9%): [8142,502, 8360,365] (assumes normal distribution)

    Secondary result "com.jmhexamples.EscapeAnalysis.bad:·gc.churn.G1_Eden_Space.norm":
      3200186,504 ±(99.9%) 44674,340 B/op [Average]
      (min, avg, max) = (3185992,411, 3200186,504, 3210602,618), stdev = 11601,784
      CI (99.9%): [3155512,164, 3244860,843] (assumes normal distribution)

    Secondary result "com.jmhexamples.EscapeAnalysis.bad:·gc.churn.G1_Survivor_Space":
      0,006 ±(99.9%) 0,002 MB/sec [Average]
      (min, avg, max) = (0,005, 0,006, 0,007), stdev = 0,001
      CI (99.9%): [0,004, 0,008] (assumes normal distribution)

    Secondary result "com.jmhexamples.EscapeAnalysis.bad:·gc.churn.G1_Survivor_Space.norm":
      2,416 ±(99.9%) 0,759 B/op [Average]
      (min, avg, max) = (2,128, 2,416, 2,679), stdev = 0,197
      CI (99.9%): [1,656, 3,175] (assumes normal distribution)

    Secondary result "com.jmhexamples.EscapeAnalysis.bad:·gc.count":
      798,000 ±(99.9%) 0,001 counts [Sum]
      (min, avg, max) = (159,000, 159,600, 160,000), stdev = 0,548
      CI (99.9%): [798,000, 798,000] (assumes normal distribution)

    Secondary result "com.jmhexamples.EscapeAnalysis.bad:·gc.time":
      727,000 ±(99.9%) 0,001 ms [Sum]
      (min, avg, max) = (142,000, 145,400, 147,000), stdev = 2,074
      CI (99.9%): [727,000, 727,000] (assumes normal distribution)


    # JMH version: 1.21
    # VM version: JDK 14.0.1, OpenJDK 64-Bit Server VM, 14.0.1+8
    # VM invoker: C:\Java\BellSoft\LibericaJDK-14\bin\java.exe
    # VM options: -javaagent:C:\JetBrains\IntelliJ IDEA Community Edition 2019.3.3\lib\idea_rt.jar=55351:C:\JetBrains\IntelliJ IDEA Community Edition 2019.3.3\bin -Dfile.encoding=UTF-8 --module-path=C:\Users\edwardhyde\IdeaProjects\jmh-benchmarks\target\classes;C:\Users\edwardhyde\.m2\repository\org\openjdk\jmh\jmh-core\1.21\jmh-core-1.21.jar;C:\Users\edwardhyde\.m2\repository\net\sf\jopt-simple\jopt-simple\4.6\jopt-simple-4.6.jar;C:\Users\edwardhyde\.m2\repository\org\apache\commons\commons-math3\3.2\commons-math3-3.2.jar;C:\Users\edwardhyde\IdeaProjects\jmh-benchmarks\\unnamed.jar -Djdk.module.main=benchmarks
    # Warmup: 5 iterations, 20000 ms each
    # Measurement: 5 iterations, 20000 ms each
    # Timeout: 10 min per iteration
    # Threads: 1 thread, will synchronize iterations
    # Benchmark mode: Average time, time/op
    # Benchmark: com.jmhexamples.EscapeAnalysis.good

    # Run progress: 50,00% complete, ETA 00:06:51
    # Warmup Fork: 1 of 1
    # Warmup Iteration   1: 371335,181 ns/op
    # Warmup Iteration   2: 371505,273 ns/op
    # Warmup Iteration   3: 371203,864 ns/op
    # Warmup Iteration   4: 366684,947 ns/op
    # Warmup Iteration   5: 370379,824 ns/op
    Iteration   1: 370432,349 ns/op
                     ·gc.alloc.rate:      ≈ 10⁻⁵ MB/sec
                     ·gc.alloc.rate.norm: 0,008 B/op
                     ·gc.count:           ≈ 0 counts

    Iteration   2: 370208,450 ns/op
                     ·gc.alloc.rate:      ≈ 10⁻⁵ MB/sec
                     ·gc.alloc.rate.norm: 0,008 B/op
                     ·gc.count:           ≈ 0 counts

    Iteration   3: 370859,041 ns/op
                     ·gc.alloc.rate:      ≈ 10⁻⁵ MB/sec
                     ·gc.alloc.rate.norm: 0,008 B/op
                     ·gc.count:           ≈ 0 counts

    Iteration   4: 370571,315 ns/op
                     ·gc.alloc.rate:      ≈ 10⁻⁵ MB/sec
                     ·gc.alloc.rate.norm: 0,008 B/op
                     ·gc.count:           ≈ 0 counts

    Iteration   5: 370455,981 ns/op
                     ·gc.alloc.rate:      ≈ 10⁻⁵ MB/sec
                     ·gc.alloc.rate.norm: 0,008 B/op
                     ·gc.count:           ≈ 0 counts


    # Run progress: 75,00% complete, ETA 00:03:25
    # Fork: 1 of 1
    # Warmup Iteration   1: 371571,581 ns/op
    # Warmup Iteration   2: 371045,511 ns/op
    # Warmup Iteration   3: 371383,510 ns/op
    # Warmup Iteration   4: 366664,495 ns/op
    # Warmup Iteration   5: 370390,402 ns/op
    Iteration   1: 370080,592 ns/op
                     ·gc.alloc.rate:      ≈ 10⁻⁵ MB/sec
                     ·gc.alloc.rate.norm: 0,008 B/op
                     ·gc.count:           ≈ 0 counts

    Iteration   2: 371328,687 ns/op
                     ·gc.alloc.rate:      ≈ 10⁻⁵ MB/sec
                     ·gc.alloc.rate.norm: 0,008 B/op
                     ·gc.count:           ≈ 0 counts

    Iteration   3: 370962,918 ns/op
                     ·gc.alloc.rate:      ≈ 10⁻⁵ MB/sec
                     ·gc.alloc.rate.norm: 0,008 B/op
                     ·gc.count:           ≈ 0 counts

    Iteration   4: 370260,689 ns/op
                     ·gc.alloc.rate:      ≈ 10⁻⁵ MB/sec
                     ·gc.alloc.rate.norm: 0,008 B/op
                     ·gc.count:           ≈ 0 counts

    Iteration   5: 370770,447 ns/op
                     ·gc.alloc.rate:      ≈ 10⁻⁵ MB/sec
                     ·gc.alloc.rate.norm: 0,008 B/op
                     ·gc.count:           ≈ 0 counts



    Result "com.jmhexamples.EscapeAnalysis.good":
      370680,667 ±(99.9%) 1967,344 ns/op [Average]
      (min, avg, max) = (370080,592, 370680,667, 371328,687), stdev = 510,913
      CI (99.9%): [368713,323, 372648,010] (assumes normal distribution)

    Secondary result "com.jmhexamples.EscapeAnalysis.good:·gc.alloc.rate":
      ≈ 10⁻⁵ MB/sec

    Secondary result "com.jmhexamples.EscapeAnalysis.good:·gc.alloc.rate.norm":
      0,008 ±(99.9%) 0,001 B/op [Average]
      (min, avg, max) = (0,008, 0,008, 0,008), stdev = 0,001
      CI (99.9%): [0,008, 0,008] (assumes normal distribution)

    Secondary result "com.jmhexamples.EscapeAnalysis.good:·gc.count":
      ≈ 0 counts


    # Run complete. Total time: 00:13:42

    REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
    why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
    experiments, perform baseline and negative tests that provide experimental control, make sure
    the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
    Do not assume the numbers tell you what you want them to tell.

    Benchmark                                            Mode  Cnt        Score       Error   Units
    EscapeAnalysis.bad                                   avgt    5   360822,685 ±   862,988   ns/op
    EscapeAnalysis.bad:·gc.alloc.rate                    avgt    5     8251,211 ±    19,855  MB/sec
    EscapeAnalysis.bad:·gc.alloc.rate.norm               avgt    5  3200097,931 ±     2,725    B/op
    EscapeAnalysis.bad:·gc.churn.G1_Eden_Space           avgt    5     8251,434 ±   108,932  MB/sec
    EscapeAnalysis.bad:·gc.churn.G1_Eden_Space.norm      avgt    5  3200186,504 ± 44674,340    B/op
    EscapeAnalysis.bad:·gc.churn.G1_Survivor_Space       avgt    5        0,006 ±     0,002  MB/sec
    EscapeAnalysis.bad:·gc.churn.G1_Survivor_Space.norm  avgt    5        2,416 ±     0,759    B/op
    EscapeAnalysis.bad:·gc.count                         avgt    5      798,000              counts
    EscapeAnalysis.bad:·gc.time                          avgt    5      727,000                  ms
    EscapeAnalysis.good                                  avgt    5   370680,667 ±  1967,344   ns/op
    EscapeAnalysis.good:·gc.alloc.rate                   avgt    5       ≈ 10⁻⁵              MB/sec
    EscapeAnalysis.good:·gc.alloc.rate.norm              avgt    5        0,008 ±     0,001    B/op
    EscapeAnalysis.good:·gc.count                        avgt    5          ≈ 0              counts




    # JMH version: 1.21
    # VM version: JDK 14.0.1, OpenJDK 64-Bit Server VM, 14.0.1+8
    # VM invoker: C:\Java\jdk14-cmp\bin\java.exe
    # VM options: -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCIProduct -XX:-UnlockExperimentalVMOptions -XX:ThreadPriorityPolicy=1 -javaagent:C:\JetBrains\IntelliJ IDEA Community Edition 2019.3.3\lib\idea_rt.jar=55511:C:\JetBrains\IntelliJ IDEA Community Edition 2019.3.3\bin -Dfile.encoding=UTF-8 --module-path=C:\Users\edwardhyde\IdeaProjects\jmh-benchmarks\target\classes;C:\Users\edwardhyde\.m2\repository\org\openjdk\jmh\jmh-core\1.21\jmh-core-1.21.jar;C:\Users\edwardhyde\.m2\repository\net\sf\jopt-simple\jopt-simple\4.6\jopt-simple-4.6.jar;C:\Users\edwardhyde\.m2\repository\org\apache\commons\commons-math3\3.2\commons-math3-3.2.jar;C:\Users\edwardhyde\IdeaProjects\jmh-benchmarks\\unnamed.jar -Djdk.module.main=benchmarks
    # Warmup: 5 iterations, 20000 ms each
    # Measurement: 5 iterations, 20000 ms each
    # Timeout: 10 min per iteration
    # Threads: 1 thread, will synchronize iterations
    # Benchmark mode: Average time, time/op
    # Benchmark: com.jmhexamples.EscapeAnalysis.bad

    # Run progress: 0,00% complete, ETA 00:13:20
    # Warmup Fork: 1 of 1
    # Warmup Iteration   1: 386028,570 ns/op
    # Warmup Iteration   2: 367474,011 ns/op
    # Warmup Iteration   3: 367108,430 ns/op
    # Warmup Iteration   4: 368352,017 ns/op
    # Warmup Iteration   5: 367756,187 ns/op
    Iteration   1: 367926,576 ns/op
                     ·gc.alloc.rate:      ≈ 10⁻⁴ MB/sec
                     ·gc.alloc.rate.norm: 0,015 B/op
                     ·gc.count:           ≈ 0 counts

    Iteration   2: 368613,061 ns/op
                     ·gc.alloc.rate:      0,050 MB/sec
                     ·gc.alloc.rate.norm: 19,998 B/op
                     ·gc.count:           ≈ 0 counts

    Iteration   3: 367953,974 ns/op
                     ·gc.alloc.rate:      0,096 MB/sec
                     ·gc.alloc.rate.norm: 38,012 B/op
                     ·gc.count:           ≈ 0 counts

    Iteration   4: 367945,397 ns/op
                     ·gc.alloc.rate:      ≈ 10⁻⁴ MB/sec
                     ·gc.alloc.rate.norm: 0,015 B/op
                     ·gc.count:           ≈ 0 counts

    Iteration   5: 367691,163 ns/op
                     ·gc.alloc.rate:      0,048 MB/sec
                     ·gc.alloc.rate.norm: 18,875 B/op
                     ·gc.count:           ≈ 0 counts


    # Run progress: 25,00% complete, ETA 00:10:16
    # Fork: 1 of 1
    # Warmup Iteration   1: 374073,616 ns/op
    # Warmup Iteration   2: 365774,812 ns/op
    # Warmup Iteration   3: 366180,631 ns/op
    # Warmup Iteration   4: 368398,331 ns/op
    # Warmup Iteration   5: 370210,660 ns/op
    Iteration   1: 372284,909 ns/op
                     ·gc.alloc.rate:      0,044 MB/sec
                     ·gc.alloc.rate.norm: 17,459 B/op
                     ·gc.count:           ≈ 0 counts

    Iteration   2: 372070,152 ns/op
                     ·gc.alloc.rate:      0,035 MB/sec
                     ·gc.alloc.rate.norm: 13,990 B/op
                     ·gc.count:           ≈ 0 counts

    Iteration   3: 370674,617 ns/op
                     ·gc.alloc.rate:      ≈ 10⁻⁴ MB/sec
                     ·gc.alloc.rate.norm: 0,016 B/op
                     ·gc.count:           ≈ 0 counts

    Iteration   4: 369696,897 ns/op
                     ·gc.alloc.rate:      ≈ 10⁻⁴ MB/sec
                     ·gc.alloc.rate.norm: 0,015 B/op
                     ·gc.count:           ≈ 0 counts

    Iteration   5: 367805,763 ns/op
                     ·gc.alloc.rate:      ≈ 10⁻⁴ MB/sec
                     ·gc.alloc.rate.norm: 0,015 B/op
                     ·gc.count:           ≈ 0 counts



    Result "com.jmhexamples.EscapeAnalysis.bad":
      370506,468 ±(99.9%) 7096,436 ns/op [Average]
      (min, avg, max) = (367805,763, 370506,468, 372284,909), stdev = 1842,922
      CI (99.9%): [363410,031, 377602,904] (assumes normal distribution)

    Secondary result "com.jmhexamples.EscapeAnalysis.bad:·gc.alloc.rate":
      0,016 ±(99.9%) 0,084 MB/sec [Average]
      (min, avg, max) = (≈ 10⁻⁴, 0,016, 0,044), stdev = 0,022
      CI (99.9%): [≈ 0, 0,099] (assumes normal distribution)

    Secondary result "com.jmhexamples.EscapeAnalysis.bad:·gc.alloc.rate.norm":
      6,299 ±(99.9%) 33,467 B/op [Average]
      (min, avg, max) = (0,015, 6,299, 17,459), stdev = 8,691
      CI (99.9%): [≈ 0, 39,765] (assumes normal distribution)

    Secondary result "com.jmhexamples.EscapeAnalysis.bad:·gc.count":
      ≈ 0 counts


    # JMH version: 1.21
    # VM version: JDK 14.0.1, OpenJDK 64-Bit Server VM, 14.0.1+8
    # VM invoker: C:\Java\jdk14-cmp\bin\java.exe
    # VM options: -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCIProduct -XX:-UnlockExperimentalVMOptions -XX:ThreadPriorityPolicy=1 -javaagent:C:\JetBrains\IntelliJ IDEA Community Edition 2019.3.3\lib\idea_rt.jar=55511:C:\JetBrains\IntelliJ IDEA Community Edition 2019.3.3\bin -Dfile.encoding=UTF-8 --module-path=C:\Users\edwardhyde\IdeaProjects\jmh-benchmarks\target\classes;C:\Users\edwardhyde\.m2\repository\org\openjdk\jmh\jmh-core\1.21\jmh-core-1.21.jar;C:\Users\edwardhyde\.m2\repository\net\sf\jopt-simple\jopt-simple\4.6\jopt-simple-4.6.jar;C:\Users\edwardhyde\.m2\repository\org\apache\commons\commons-math3\3.2\commons-math3-3.2.jar;C:\Users\edwardhyde\IdeaProjects\jmh-benchmarks\\unnamed.jar -Djdk.module.main=benchmarks
    # Warmup: 5 iterations, 20000 ms each
    # Measurement: 5 iterations, 20000 ms each
    # Timeout: 10 min per iteration
    # Threads: 1 thread, will synchronize iterations
    # Benchmark mode: Average time, time/op
    # Benchmark: com.jmhexamples.EscapeAnalysis.good

    # Run progress: 50,00% complete, ETA 00:06:51
    # Warmup Fork: 1 of 1
    # Warmup Iteration   1: 373897,443 ns/op
    # Warmup Iteration   2: 365874,530 ns/op
    # Warmup Iteration   3: 366433,734 ns/op
    # Warmup Iteration   4: 367394,106 ns/op
    # Warmup Iteration   5: 367784,118 ns/op
    Iteration   1: 367467,373 ns/op
                     ·gc.alloc.rate:      ≈ 10⁻⁴ MB/sec
                     ·gc.alloc.rate.norm: 0,015 B/op
                     ·gc.count:           ≈ 0 counts

    Iteration   2: 367789,690 ns/op
                     ·gc.alloc.rate:      0,001 MB/sec
                     ·gc.alloc.rate.norm: 0,435 B/op
                     ·gc.count:           ≈ 0 counts

    Iteration   3: 367356,357 ns/op
                     ·gc.alloc.rate:      0,003 MB/sec
                     ·gc.alloc.rate.norm: 1,319 B/op
                     ·gc.count:           ≈ 0 counts

    Iteration   4: 367304,988 ns/op
                     ·gc.alloc.rate:      ≈ 10⁻⁴ MB/sec
                     ·gc.alloc.rate.norm: 0,015 B/op
                     ·gc.count:           ≈ 0 counts

    Iteration   5: 367765,396 ns/op
                     ·gc.alloc.rate:      ≈ 10⁻⁴ MB/sec
                     ·gc.alloc.rate.norm: 0,015 B/op
                     ·gc.count:           ≈ 0 counts


    # Run progress: 75,00% complete, ETA 00:03:25
    # Fork: 1 of 1
    # Warmup Iteration   1: 373559,937 ns/op
    # Warmup Iteration   2: 365233,338 ns/op
    # Warmup Iteration   3: 365493,975 ns/op
    # Warmup Iteration   4: 367670,232 ns/op
    # Warmup Iteration   5: 367596,684 ns/op
    Iteration   1: 367520,107 ns/op
                     ·gc.alloc.rate:      0,003 MB/sec
                     ·gc.alloc.rate.norm: 1,028 B/op
                     ·gc.count:           ≈ 0 counts

    Iteration   2: 367849,190 ns/op
                     ·gc.alloc.rate:      0,006 MB/sec
                     ·gc.alloc.rate.norm: 2,405 B/op
                     ·gc.count:           ≈ 0 counts

    Iteration   3: 378355,497 ns/op
                     ·gc.alloc.rate:      0,042 MB/sec
                     ·gc.alloc.rate.norm: 17,141 B/op
                     ·gc.count:           ≈ 0 counts

    Iteration   4: 368421,151 ns/op
                     ·gc.alloc.rate:      ≈ 10⁻⁴ MB/sec
                     ·gc.alloc.rate.norm: 0,015 B/op
                     ·gc.count:           ≈ 0 counts

    Iteration   5: 368169,258 ns/op
                     ·gc.alloc.rate:      ≈ 10⁻⁴ MB/sec
                     ·gc.alloc.rate.norm: 0,108 B/op
                     ·gc.count:           ≈ 0 counts



    Result "com.jmhexamples.EscapeAnalysis.good":
      370063,041 ±(99.9%) 17897,698 ns/op [Average]
      (min, avg, max) = (367520,107, 370063,041, 378355,497), stdev = 4647,975
      CI (99.9%): [352165,342, 387960,739] (assumes normal distribution)

    Secondary result "com.jmhexamples.EscapeAnalysis.good:·gc.alloc.rate":
      0,010 ±(99.9%) 0,069 MB/sec [Average]
      (min, avg, max) = (≈ 10⁻⁴, 0,010, 0,042), stdev = 0,018
      CI (99.9%): [≈ 0, 0,080] (assumes normal distribution)

    Secondary result "com.jmhexamples.EscapeAnalysis.good:·gc.alloc.rate.norm":
      4,140 ±(99.9%) 28,230 B/op [Average]
      (min, avg, max) = (0,015, 4,140, 17,141), stdev = 7,331
      CI (99.9%): [≈ 0, 32,370] (assumes normal distribution)

    Secondary result "com.jmhexamples.EscapeAnalysis.good:·gc.count":
      ≈ 0 counts


    # Run complete. Total time: 00:13:42

    REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
    why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
    experiments, perform baseline and negative tests that provide experimental control, make sure
    the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
    Do not assume the numbers tell you what you want them to tell.

    Benchmark                                Mode  Cnt       Score       Error   Units
    EscapeAnalysis.bad                       avgt    5  370506,468 ±  7096,436   ns/op
    EscapeAnalysis.bad:·gc.alloc.rate        avgt    5       0,016 ±     0,084  MB/sec
    EscapeAnalysis.bad:·gc.alloc.rate.norm   avgt    5       6,299 ±    33,467    B/op
    EscapeAnalysis.bad:·gc.count             avgt    5         ≈ 0              counts
    EscapeAnalysis.good                      avgt    5  370063,041 ± 17897,698   ns/op
    EscapeAnalysis.good:·gc.alloc.rate       avgt    5       0,010 ±     0,069  MB/sec
    EscapeAnalysis.good:·gc.alloc.rate.norm  avgt    5       4,140 ±    28,230    B/op
    EscapeAnalysis.good:·gc.count            avgt    5         ≈ 0              counts

     */
    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(EscapeAnalysis.class.getSimpleName())
                .addProfiler(GCProfiler.class)
                .forks(1)
                .build();
        new Runner(opt).run();
    }

    public long accumulateInLoop(final ThreadLocalRandom rnd) {
        final Vector2D v = Vector2D.randomVector(rnd);
        for (int i = 0; i < SIZE; i++) {
            final Vector2D addition = Vector2D.randomVector(rnd);
            v.addAccumulate(addition);
        }
        return (long) v.length();
    }

    public long replaceInLoop(final ThreadLocalRandom rnd) {
        Vector2D v = Vector2D.randomVector(rnd);
        for (int i = 0; i < SIZE; i++) {
            final Vector2D addition = Vector2D.randomVector(rnd);
            v = v.add(addition);
        }
        return (long) v.length();
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5, time = 20000, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 5, time = 20000, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    public void good(Blackhole blackhole) {
        blackhole.consume(accumulateInLoop(ThreadLocalRandom.current()));
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5, time = 20000, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 5, time = 20000, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    public void bad(Blackhole blackhole) {
        blackhole.consume(replaceInLoop(ThreadLocalRandom.current()));
    }
}
