## 公众号(纯洁编程说：chunjie_tech)

## synchronized同步方法的八种使用场景

介绍8种同步方法的访问场景，看看多线程访问同步方法是否还是线程安全的。

#### 1、两个线程同时访问同一个对象的同步方法

分析：这种情况是经典的对象锁中的方法锁，两个线程争夺同一个对象锁，所以会相互等待，是线程安全的。

两个线程同时访问同一个对象的同步方法，是线程安全的。

#### 2、两个线程同时访问两个对象的同步方法

这种场景就是对象锁失效的场景，原因出在访问的是两个对象的同步方法，那么这两个线程分别持有的两个线程的锁，所以是互相不会受限的。加锁的目的是为了让多个线程竞争同一把锁，而这种情况多个线程之间不再竞争同一把锁，而是分别持有一把锁.

所以结论是：两个线程同时访问两个对象的同步方法，是线程不安全的。

代码如下：

Gitee：

[两个线程同时访问两个对象的同步方法](https://gitee.com/zjhpure/studyNotes/tree/master/src/main/java/sync/Condition2.java)

GitHub：

[两个线程同时访问两个对象的同步方法](https://github.com/zjhpure/studyNotes/tree/master/src/main/java/sync/Condition2.java)

分析：

两个线程(thread1、thread2)，访问两个对象(instance1、instance2)的同步方法(method())，两个线程都有各自的锁，不能形成两个线程竞争一把锁的局势，所以这时，synchronized修饰的方法method()和不用synchronized修饰的效果一样(把synchronized关键字去掉，运行结果一样)，所以此时的method()只是个普通方法。

解决：

若要使锁生效，只需将method()方法用static修饰，这样就形成了类锁，多个实例(instance1、instance2)共同竞争一把类锁，就可以使两个线程串行执行了。

#### 3、两个线程同时访问(一个或两个)对象的静态同步方法

这个场景解决的是场景二中出现的线程不安全问题，即用类锁实现：

两个线程同时访问(一个或两个)对象的静态同步方法，是线程安全的。

#### 4、两个线程分别同时访问(一个或两个)对象的同步方法和非同步方法

这个场景是两个线程其中一个访问同步方法，另一个访问非同步方法，此时程序会不会串行执行呢，也就是说是不是线程安全的呢？

我们可以确定是线程不安全的，如果方法不加synchronized都是安全的，那就不需要同步方法了。验证下我们的结论：

两个线程分别同时访问(一个或两个)对象的同步方法和非同步方法，是线程不安全的。

代码如下：

Gitee：

[两个线程分别同时访问(一个或两个)对象的同步方法和非同步方法](https://gitee.com/zjhpure/studyNotes/tree/master/src/main/java/sync/Condition4.java)

GitHub：

[两个线程分别同时访问(一个或两个)对象的同步方法和非同步方法](https://github.com/zjhpure/studyNotes/tree/master/src/main/java/sync/Condition4.java)

运行结果：

两个线程是并行执行的，所以是线程不安去的。

结果分析：

问题在于此，method1没有被synchronized修饰，所以不会受到锁的影响。即便是在同一个对象中，当然在多个实例中，更不会被锁影响了。

结论：

非同步方法不受其它由synchronized修饰的同步方法影响

#### 5、两个线程访问同一个对象中的同步方法，同步方法又调用一个非同步方法

代码如下：

Gitee：

[两个线程访问同一个对象中的同步方法，同步方法又调用一个非同步方法](https://gitee.com/zjhpure/studyNotes/tree/master/src/main/java/sync/Condition5.java)

GitHub：

[两个线程访问同一个对象中的同步方法，同步方法又调用一个非同步方法](https://github.com/zjhpure/studyNotes/tree/master/src/main/java/sync/Condition5.java)

结果分析：

我们可以看出，普通方法被两个线程并行执行，不是线程安全的。这是为什么呢？

因为如果非同步方法，有任何其他线程直接调用，而不是仅在调用同步方法时，才调用非同步方法，此时会出现多个线程并行执行非同步方法的情况，线程就不安全了。

对于同步方法中调用非同步方法时，要想保证线程安全，就必须保证非同步方法的入口，仅出现在同步方法中。但这种控制方式不够优雅，若被不明情况的人直接调用非同步方法，就会导致原有的线程同步不再安全。所以不推荐大家在项目中这样使用，但我们要理解这种情况，并且我们要用语义明确的、让人一看就知道这是同步方法的方式，来处理线程安全的问题。

所以，最简单的方式，是在非同步方法上，也加上synchronized关键字，使其变成一个同步方法，这样就变成了《场景五：两个线程同时访问同一个对象的不同的同步方法》，这种场景下，大家就很清楚的看到，同一个对象中的两个同步方法，不管哪个线程调用，都是线程安全的了。

结论：

两个线程访问同一个对象中的同步方法，同步方法又调用一个非同步方法，仅在没有其他线程直接调用非同步方法的情况下，是线程安全的。若有其他线程直接调用非同步方法，则是线程不安全的。

#### 6、两个线程同时访问同一个对象的不同的同步方法

对象锁的作用范围是对象中的所有同步方法，所以，当访问同一个对象中的多个同步方法时，结论是：

两个线程同时访问同一个对象的不同的同步方法时，是线程安全的。

代码如下：

Gitee：

[两个线程同时访问同一个对象的不同的同步方法](https://gitee.com/zjhpure/studyNotes/tree/master/src/main/java/sync/Condition6.java)

GitHub：

[两个线程同时访问同一个对象的不同的同步方法](https://github.com/zjhpure/studyNotes/tree/master/src/main/java/sync/Condition6.java)

运行结果：

是线程安全的。

结果分析：

两个方法(method0()和method1())的synchronized修饰符，虽没有指定锁对象，但默认锁对象为this对象为锁对象，

所以对于同一个实例(instance)，两个线程拿到的锁是同一把锁，此时同步方法会串行执行。

这也是synchronized关键字的可重入性的一种体现。

#### 7、两个线程分别同时访问静态synchronized和非静态synchronized方法

这种场景的本质也是在探讨两个线程获取的是不是同一把锁的问题。

静态synchronized方法属于类锁，锁对象是(*.class)对象，非静态synchronized方法属于对象锁中的方法锁，锁对象是this对象。两个线程拿到的是不同的锁，自然不会相互影响。

结论：

两个线程分别同时访问静态synchronized和非静态synchronized方法，线程不安全。

Gitee：

[两个线程分别同时访问静态synchronized和非静态synchronized方法](https://gitee.com/zjhpure/studyNotes/tree/master/src/main/java/sync/Condition7.java)

GitHub：

[两个线程分别同时访问静态synchronized和非静态synchronized方法](https://github.com/zjhpure/studyNotes/tree/master/src/main/java/sync/Condition7.java)

#### 8、同步方法抛出异常后，JVM会自动释放锁的情况

本场景探讨的是synchronized释放锁的场景：

只有当同步方法执行完或执行时抛出异常这两种情况，才会释放锁。

所以，在一个线程的同步方法中出现异常的时候，会释放锁，另一个线程得到锁，继续执行。

而不会出现一个线程抛出异常后，另一个线程一直等待获取锁的情况。这是因为JVM在同步方法抛出异常的时候，会自动释放锁对象。

Gitee：

[同步方法抛出异常后，JVM会自动释放锁的情况](https://gitee.com/zjhpure/studyNotes/tree/master/src/main/java/sync/Condition8.java)

GitHub：

[同步方法抛出异常后，JVM会自动释放锁的情况](https://github.com/zjhpure/studyNotes/tree/master/src/main/java/sync/Condition8.java)

结果分析：

可以看出线程还是串行执行的，说明是线程安全的。而且出现异常后，不会造成死锁现象，JVM会自动释放出现异常线程的锁对象，其他线程获取锁继续执行。