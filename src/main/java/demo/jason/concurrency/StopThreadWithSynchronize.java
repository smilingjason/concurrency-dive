package demo.jason.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * Created by jason on 6/1/14.
 */
public class StopThreadWithSynchronize {

    private static boolean stop = false;

    private static synchronized void stopCount() {
        stop = true;
    }

    private static synchronized boolean shouldStop() {
        return stop == true;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread a = new Thread(() ->{
            System.out.println("Count thread started");
            long i = 0;
            while(!shouldStop())
                i++;
            System.out.println("i = " + i);
        });

        a.start();

        TimeUnit.SECONDS.sleep(1);

        stopCount();
        System.out.println("StopThreadWithSynchronize Main thread existed");
    }
}
