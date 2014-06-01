package demo.jason.concurrency;

import java.util.concurrent.CountDownLatch;

/**
 * Created by jason on 6/1/14.
 */
public class DeadLockWithCountDown {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello World!");
        A a = new A();
        B b = new B();
        CountDownLatch count = new CountDownLatch(2);

        Runnable rA = () -> {
            synchronized (a) {
                count.countDown();
                try {
                    count.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b) {

                }
            }
        };
        Runnable rB = () -> {
            synchronized (b) {
                count.countDown();
                try {
                    count.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a) {

                }
            }
        };

        new Thread(rA).start();
        new Thread(rB).start();

        Thread.sleep(Integer.MAX_VALUE);
    }

    static class A {
    }

    static class B {
    }
}
