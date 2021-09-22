package sync;

/**
 * @author zhangjianhua
 * @date 2021-09-18 18:00
 * @description 两个线程同时访问两个对象的同步方法
 */
public class Condition2 implements Runnable {

    // 创建两个不同的对象
    static Condition2 instance1 = new Condition2();

    static Condition2 instance2 = new Condition2();

    @Override
    public void run() {
        method();
    }

    private synchronized void method() {
        System.out.println("线程名：" + Thread.currentThread().getName() + "，运行开始");

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("线程名：" + Thread.currentThread().getName() + "，运行结束");
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(instance1);
        Thread thread2 = new Thread(instance2);

        thread1.start();
        thread2.start();

        while (thread1.isAlive() || thread2.isAlive()) {
        }

        System.out.println("测试结束");
    }

}
