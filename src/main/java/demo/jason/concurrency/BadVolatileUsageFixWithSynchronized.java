package demo.jason.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Key point:
 * 1. volatile only guarantee the memory visibility, not the operation atomic
 * 2. while synchronized can guarantee both
 * Created by jason on 6/1/14.
 */
public class BadVolatileUsageFixWithSynchronized {
    private static long sequenceNumber = 1;

    public static synchronized long getSequenceNumber() {
        return sequenceNumber++;
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();

        es.submit(() -> {
            System.out.println("sub thread 1 started");
            for(int i = 0; i < 10000; i++) {
               getSequenceNumber();
            }
            System.out.println("sub thread 1 stopped");
        });
        es.submit(() -> {
            System.out.println("sub thread 2 started");
            for(int i = 0; i < 10000; i++) {
               getSequenceNumber();
            }
            System.out.println("sub thread 2 stopped");
        });
        es.shutdown();
        es.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        //next one is expected to be 20001, is it right? and why?
        System.out.println("next = " + getSequenceNumber());
    }
}
