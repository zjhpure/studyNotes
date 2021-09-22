package sync;

/**
 * @author zhangjianhua
 * @date 2021-09-22 17:42
 * @description 同步方法抛出异常后，JVM会自动释放锁的情况
 */
public class Condition8 implements Runnable {

    private static Condition8 instance = new Condition8();

    @Override
    public void run() {
        if (Thread.currentThread().getName().equals("Thread-0")) {
            // 线程0,执行抛异常方法method0()
            method0();
        }
        if (Thread.currentThread().getName().equals("Thread-1")) {
            // 线程1,执行正常方法method1()
            method1();
        }
    }

    private synchronized void method0() {
        System.out.println("线程名：" + Thread.currentThread().getName() + "，运行开始");

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 同步方法中，当抛出异常时，JVM会自动释放锁，不需要手动释放，其他线程即可获取到该锁
        System.out.println("线程名：" + Thread.currentThread().getName() + "，抛出异常，释放锁");

        throw new RuntimeException();
    }

    private synchronized void method1() {
        System.out.println("线程名：" + Thread.currentThread().getName() + "，运行开始");

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("线程名：" + Thread.currentThread().getName() + "，运行结束");
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(instance);
        Thread thread2 = new Thread(instance);

        thread1.start();
        thread2.start();

        while (thread1.isAlive() || thread2.isAlive()) {
        }

        System.out.println("测试结束");
    }

}
