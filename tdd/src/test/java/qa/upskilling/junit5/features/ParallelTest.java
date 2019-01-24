package qa.upskilling.junit5.features;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.CONCURRENT)
class ParallelTest {

    public static final int SLEEP = 5_000;

    @Test
    void firstTest() throws InterruptedException {
        Thread.sleep(SLEEP);
        System.out.println("first test " + Thread.currentThread().getName());
    }

    @Test
    void secondTest() throws InterruptedException {
        Thread.sleep(SLEEP);
        System.out.println("second test " + Thread.currentThread().getName());
    }

    @Test
    void thirdTest() throws InterruptedException {
        Thread.sleep(SLEEP);
        System.out.println("third test " + Thread.currentThread().getName());
    }

    @Test
    void fourthTest() throws InterruptedException {
        Thread.sleep(SLEEP);
        System.out.println("fourth test " + Thread.currentThread().getName());
    }
}


