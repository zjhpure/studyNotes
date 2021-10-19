## 公众号(纯洁编程说：chunjie_tech)

## 分布式锁

#### 分布式锁三种实现方式

1、基于数据库实现分布式锁。

1.1 通过悲观锁实现

利用select * from table where 条件 for update这种排他锁

注意where条件，“where name = lock”，name字段必须走索引，否则会锁表。

有些情况下，比如表不大，MySQL优化器会不走这个索引，导致锁表问题。

记得以前开发时有时会莫名出现锁表，现在回想可能是当时一边在用系统，另一边在数据库工具里用for update来查数据，结果导致锁表了。

1.2 通过乐观锁实现

乐观锁基于CAS思想，不具有互斥性，不会产生锁等待而消耗资源，操作过程中认为不存在并发冲突，只有update version失败后才能觉察到。

例如抢购、秒杀可以用这种方法来防止超卖。

可以通过增加递增的版本号字段来实现乐观锁。

select version, goodNum from tableA where id = "xxx";

update tableA set goodNum = xx, version = oldVersion + 1 where id = "xxx" and version = oldVersion;

如果一个线程在执行第一条语句获取到version后被另一个线程抢占了，那么另一个线程如果执行完了这两条语句，version就会更新为newVersion；
等这个线程再执行时，执行第二条语句，因为version已被更新，所以这条update语句更新的行数是0，这就避免了超卖。

2、基于Redis实现分布式锁。

setnx命令：当且仅当key不存在时，set一个key为val的字符串，返回1；若key存在，则什么都不做，返回0。

加锁：
使用setnx命令加锁，再使用expire命令为锁添加超时时间，超过该时间则自动释放锁，key设置为具体的锁标识。
方法中不断轮询，如果锁还存在，睡眠10ms再轮询，直到锁不存在了，再执行setnx命令和expire命令。

释放锁：
方法中不断轮询，使用del命令释放锁，如果删除失败或者抛出异常也不影响，因为设置了过期时间，到期后也会自动删除。

3、基于Zookeeper实现分布式锁。

#### 各种锁对比

数据库分布式锁：
缺点：
1、数据操作性能较差，也有锁表的风险。
2、非阻塞操作失败后，需要轮询，占用cpu资源。
3、长时间不commit或者长时间轮询，可能会占用较多连接资源。

Redis分布式锁：
缺点：
1、如果锁删除失败，过期时间不好控制。
2、非阻塞操作失败后，需要轮询，占用cpu资源。

Zookeeper分布式锁：
缺点：性能不如Redis分布式锁，主要原因是写操作(获取锁和释放锁)都需要在Leader上执行，然后同步到follower。
优点：ZooKeeper有较好的性能和可靠性。

从理解的难易程度角度(从低到高)：数据库 > Redis > Zookeeper

从实现的复杂性角度(从低到高)：Zookeeper >= Redis > 数据库

从性能角度(从高到低)：Redis > Zookeeper >= 数据库

从可靠性角度(从高到低)：Zookeeper > Redis > 数据库



