## 源码路径

/src/main/java/org/pure/studyNotes/java/mulThread/printlnOrder/Main.java

## 题目地址(按序打印)

https://leetcode-cn.com/problems/print-in-order

## 题目描述

```
我们提供了一个类：

public class Foo {
  public void first() { print("first"); }
  public void second() { print("second"); }
  public void third() { print("third"); }
}
三个不同的线程A、B、C将会共用一个Foo实例。

一个将会调用first()方法
一个将会调用second()方法
还有一个将会调用third()方法
请设计修改程序，以确保second()方法在first()方法之后被执行，third()方法在second()方法之后被执行。

示例1：

输入: [1,2,3]
输出: "firstsecondthird"
解释: 
有三个线程会被异步启动。
输入[1,2,3]表示线程A将会调用first()方法，线程B将会调用second()方法，线程C将会调用third()方法。
正确的输出是"firstsecondthird"。

示例2：

输入: [1,3,2]
输出: "firstsecondthird"
解释: 
输入[1,3,2]表示线程A将会调用first()方法，线程B将会调用third()方法，线程C将会调用second()方法。
正确的输出是"firstsecondthird"。

注意：

尽管输入中的数字似乎暗示了顺序，但是我们并不保证线程在操作系统中的调度顺序。
你看到的输入格式主要是为了确保测试的全面性。
```

## 代码

- 语言：Java

```
class Foo {

    // 引入AtomicInteger变量，使得程序能在多线程状态下正常运行

    private AtomicInteger firstJobDone = new AtomicInteger(0);
    private AtomicInteger secondJobDone = new AtomicInteger(0);

    public Foo() {
        
    }

    public void first(Runnable printFirst) throws InterruptedException {
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        // mark the first job as done, by increasing its count.
        firstJobDone.incrementAndGet();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        while (firstJobDone.get() != 1) {
            // waiting for the first job to be done.
        }
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        // mark the second as done, by increasing its count.
        secondJobDone.incrementAndGet();
    }

    public void third(Runnable printThird) throws InterruptedException {
        while (secondJobDone.get() != 1) {
            // waiting for the second job to be done.
        }
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }

}
```

