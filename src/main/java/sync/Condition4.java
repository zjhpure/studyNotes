package sync;

/**
 * @author zhangjianhua
 * @date 2021-09-22 16:55
 * @description 两个线程分别同时访问(一个或两个)对象的同步方法和非同步方法
 */
public class Condition4 implements Runnable {

    static Condition4 instance = new Condition4();

    @Override
    public void run() {
        //两个线程访问同步方法和非同步方法
        if (Thread.currentThread().getName().equals("Thread-0")) {
            //线程0,执行同步方法method0()
            method0();
        }
        if (Thread.currentThread().getName().equals("Thread-1")) {
            //线程1,执行非同步方法method1()
            method1();
        }
    }

    // 同步方法
    private synchronized void method0() {
        System.out.println("线程名：" + Thread.currentThread().getName() + "，同步方法，运行开始");

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("线程名：" + Thread.currentThread().getName() + "，同步方法，运行结束");
    }

    // 普通方法
    private void method1() {
        System.out.println("线程名：" + Thread.currentThread().getName() + "，普通方法，运行开始");

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("线程名：" + Thread.currentThread().getName() + "，普通方法，运行结束");
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
