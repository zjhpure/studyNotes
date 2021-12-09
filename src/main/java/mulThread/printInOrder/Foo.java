package mulThread.printInOrder;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangjianhua
 * @date 2021-10-27 12:28
 * @description 按序打印
 */
public class Foo {

    // 引入AtomicInteger变量，使得程序能在多线程状态下正常运行

    private AtomicInteger firstJobDone = new AtomicInteger(0);
    private AtomicInteger secondJobDone = new AtomicInteger(0);

    public Foo() {

    }

    void first() {
        System.out.print("first");
        // mark the first job as done, by increasing its count.
        firstJobDone.incrementAndGet();
    }

    void second() {
        while (firstJobDone.get() != 1) {
            // waiting for the first job to be done.
        }
        System.out.print("second");
        // mark the second as done, by increasing its count.
        secondJobDone.incrementAndGet();
    }

    void third() {
        while (secondJobDone.get() != 1) {
            // waiting for the second job to be done.
        }
        System.out.print("third");
    }

}
