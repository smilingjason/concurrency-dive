package demo.jason.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Key point:
 * 1) Using AtomicXXXX will get better performance than synchronized so long as
 * it can work well.
 * Created by jason on 6/1/14.
 */
public class BadVolatileFixWithAtomicLong {
    private static AtomicLong sequenceNumber = new AtomicLong(0);

    public static long getSequenceNumber() {
        return sequenceNumber.incrementAndGet();
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
