package sync;

/**
 * @author zhangjianhua
 * @date 2021-09-22 17:19
 * @description 两个线程同时访问同一个对象的不同的同步方法
 */
public class Condition6 implements Runnable {

    static Condition6 instance = new Condition6();

    @Override
    public void run() {
        if (Thread.currentThread().getName().equals("Thread-0")) {
            // 线程0,执行同步方法method0()
            method0();
        }
        if (Thread.currentThread().getName().equals("Thread-1")) {
            // 线程1,执行同步方法method1()
            method1();
        }
    }

    private synchronized void method0() {
        System.out.println("线程名：" + Thread.currentThread().getName() + "，同步方法0，运行开始");

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("线程名：" + Thread.currentThread().getName() + "，同步方法0，运行结束");
    }

    private synchronized void method1() {
        System.out.println("线程名：" + Thread.currentThread().getName() + "，同步方法1，运行开始");

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("线程名：" + Thread.currentThread().getName() + "，同步方法1，运行结束");
    }

    // 运行结果:串行
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
