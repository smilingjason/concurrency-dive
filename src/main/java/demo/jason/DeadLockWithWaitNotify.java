package demo.jason;

/**
 * Hello world!
 */
public class DeadLockWithWaitNotify {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello World!");
        A a = new A();
        B b = new B();
        MyCount myCount = new MyCount();

        Runnable rA = () -> {
            synchronized (a) {
                synchronized (myCount) {
                    myCount.countDown();
                    while (!myCount.shallIgo()) {
                        try {
                            myCount.wait();
                        } catch (InterruptedException e) {
                            //ignore
                        }
                    }
                }
                synchronized (b) {

                }
            }
        };
        Runnable rB = () -> {
            synchronized (b) {
                synchronized (myCount) {
                    myCount.countDown();
                    while (!myCount.shallIgo()) {
                        try {
                            myCount.wait();
                        } catch (InterruptedException e) {
                            //ignore
                        }
                    }
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

    static class MyCount {

        int n = 2;

        public synchronized boolean shallIgo() {
            return n == 0;
        }

        public synchronized void countDown() {
            n--;
            notifyAll();
        }
    }

}
