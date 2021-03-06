## 公众号(纯洁编程说：chunjie_tech)

#### 为什么要用Redis而不用Map做缓存

1、Redis可以用几十g内存来做缓存，Map不行，一般jvm也就分几个g数据就够大了

2、Redis的缓存可以持久化，Map是内存对象，程序一重启数据就没了

3、Redis可以实现分布式的缓存，Map只能存在创建它的程序里

4、Redis可以处理每秒百万级的并发，是专业的缓存服务，Map只是一个普通的对象

5、Redis缓存有过期机制，Map本身无此功能

6、Redis有丰富的api，Map就简单太多了

7、Redis数据可持久化保存，有些缓存你想重启程序后还能继续使用，那么Map实现不了

8、Redis可以实现分布式部署，只要涉及到多进程的，Map就实现不了

9、Redis有很多数据结构，方便操作，比如hash set list sort-set等，在某些场景下操作起来比Map方便

10、Redis支持集群模式，Map做不到

11、Redis是c写的，稳定性和性能比Map更好

12、Redis可以独立部署，这样网站代码更新后Redis缓存的数据还在，本地内存每次网站更新都会释放掉数据存到Redis，多个项目间可以共享缓存数据，如果是本地内存是无法跨项目共享的，本地缓存不方面查看及修改 

13、Redis有丰富的工具管理缓存数据，Map没有

#### Redis持久化

Redis提供两种方式持久化：

1、RDB持久化，原理：Redis在内存中的数据库记录定时dump到磁盘上的RDB持久化。

2、AOF持久化，原理：Redis的操作日志以追加的方式写入文件。

两种方式的区别：

1、RDB持久化是指在指定的时间间隔内将内存中的数据集快照写入磁盘，实际操作过程是fork一个子进程，先将数据集写入临时文件，写入成功后，再替换之前的文件，用二进制压缩存储。

2、AOF持久化以日志的形式记录服务器所处理的每一个写和删除操作，查询操作不会记录，以文本的方式记录，可以打开文件看到详细的操作记录。

RDB的优点：

1、Redis备份文件只包含一个文件，对文件备份很好的。比如，可能每个小时归档一次最近24小时的数据，同时还要每天归档一次最近30天的数据。通过这样的备份策略，一旦系统出现灾难性故障，我们可以非常容易的进行恢复。

2、对于灾难恢复而言，RDB是非常不错的选择，因为我们可以非常轻松的将一个单独的文件压缩后再转移到其它存储介质上。

3、性能最大化，对于Redis的服务进程而言，在开始持久化时，它唯一需要做的只是fork出子进程，之后再由子进程完成这些持久化的工作，这样就可以极大的避免服务进程执行IO操作了。

4、相比于AOF机制，如果数据集很大，RDB的启动效率会更高。

RDB缺点：

1、如果你想保证数据的高可用性，即最大限度的避免数据丢失，那么RDB将不是一个很好的选择。因为系统一旦在定时持久化之前出现宕机现象，此前没有来得及写入磁盘的数据都将丢失。

2、由于RDB是通过fork子进程来协助完成数据持久化工作的，因此，如果当数据集较大时，可能会导致整个服务器停止服务几百毫秒，甚至是1秒钟。

AOF优点：

1、该机制可以带来更高的数据安全性，即数据持久性。Redis中提供了3种同步策略，即每秒同步、每修改同步和不同步。事实上，每秒同步也是异步完成的，其效率也是非常高的，所差的是一旦系统出现宕机现象，那么这一秒钟之内修改的数据将会丢失。而每修改同步，我们可以将其视为同步持久化，即每次发生的数据变化都会被立即记录到磁盘中。可以预见，这种方式在效率上是最低的。

2、由于该机制对日志文件的写入操作采用的是append模式，因此在写入过程中即使出现宕机现象，也不会破坏日志文件中已经存在的内容。然而如果我们本次操作只是写入了一半数据就出现了系统崩溃问题，不用担心，在Redis下一次启动之前，我们可以通过redis-check-aof工具来帮助我们解决数据一致性的问题。

3、如果日志过大，Redis可以自动启用rewrite机制。即Redis以append模式不断的将修改数据写入到老的磁盘文件中，同时Redis还会创建一个新的文件用于记录此期间有哪些修改命令被执行。因此在进行rewrite切换时可以更好的保证数据安全性。

4、AOF包含一个格式清晰、易于理解的日志文件用于记录所有的修改操作，我们也可以通过该文件完成数据的重建。

AOF缺点：

1、对于相同数量的数据集而言，AOF文件通常要大于RDB文件，RDB在恢复大数据集时的速度比AOF的恢复速度要快。

2、根据同步策略的不同，AOF在运行效率上往往会慢于RDB。

使用：

1、RDB和AOF建议同时打开(Redis4.0之后支持)。

2、RDB做冷备，AOF做数据恢复(数据更可靠)。

3、RDB采取默认配置即可，AOF推荐采取everysec每秒策略。

如何操作Redis恢复数据？

1、停止Redis进程

2、删除坏掉的RDB和AOF持久化文件。

3、修改配置文件关闭Redis的AOF持久化。

4、找到最新备份的RDB文件扔到Redis的持久化目录里。

5、启动Redis进程。

6、执行set appendonly yes动态打开AOF持久化。(不能直接开启着AOF启动Redis，因为可能是空的，这样一启动AOF马上会把Redis生成空的)

7、重启redis进程。

#### 缓存穿透

指的是对某个一定不存在的数据进行请求，该请求将会穿透缓存直达数据库。

解决方案：

1、如果缓存查不到数据，数据库也查不到数据，那么缓存添加一个这样的key，值设置为空，当下次再来时，就直接走缓存了。

2、如果别人每次都构造一个随机的key进来，那么就会被塞满，所以可以也设置一个过期时间，但是这个时间可以设置短一些，比如几分钟。
   
这里可以引入布隆过滤器，把所有可能存在的数据保存到布隆过滤器中，先走布隆过滤器的判断，根据布隆过滤器的原理，如果布隆过滤器判断一定不存在，那么就一定不存在，这时候直接就结束了，如果布隆过滤器判断存在，那么就有很大的可能存在，这时候走到缓存，所以通过布隆过滤器就能过滤掉大部分不存在的数据。

#### 缓存雪崩

指的是由于数据没有被加载到缓存中，或者缓存数据在同一时间大面积过期或失效，又或者缓存服务器宕机，导致大量的请求直达数据库。

解决方案：

1、业务设计时的失效时间加上一个随机值，尽量保持各个不相同，不在短时间内突然出现大量过期的。

2、如果真的穿透了，那么使用熔断机制，限制到一定的流量，超过了客户端就给个提示，保证部分用户能使用。

#### 缓存击穿

跟缓存雪崩类似，缓存雪崩是大量的key失效，而缓存击穿是一个热点的key，有大量请求集中对其进行访问，因为突然这个key失效了，导致大量请求直达数据库。

解决方案：

1、如果业务允许，对热点的key不设置过期时间。

2、对于热点的key的数据库语句加上for update锁住。

