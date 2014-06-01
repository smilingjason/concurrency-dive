package demo.jason.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * Created by jason on 6/1/14.
 */
public class StopThreadWrong {

    private static boolean stop = false;

    public static void main(String[] args) throws InterruptedException {
        Thread a = new Thread(() ->{
            System.out.println("Count thread started");
            long i = 0;
            while(!stop)
                i++;
            System.out.println("i = " + i);
        });

        a.start();

        TimeUnit.SECONDS.sleep(1);

        stop = true;
        System.out.println("StopThreadWrong Main thread existed");
    }
}
