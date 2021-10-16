## 公众号(纯洁编程说：chunjie_tech)

## 连接池

常见连接池：Druid、C3P0、DBCP、HikariCP

使用连接池的原因：

在执行JDBC的增删改查的操作时，如果每一次操作都来一次打开连接，操作，关闭连接，那么创建和销毁JDBC连接的开销就太大了，为了避免频繁地创建和销毁JDBC连接，我们可以通过连接池复用已经创建好的连接。

通过连接池获取连接时，并不需要指定JDBC的相关URL、用户名、口令等信息，因为这些信息已经存储在连接池内部了。

一开始，连接池内部并没有连接，所以，第一次调用ds.getConnection()，会迫使连接池内部先创建一个Connection，再返回给客户端使用。

当我们调用conn.close()方法时，不是真正“关闭”连接，而是释放到连接池中，以便下次获取连接时能直接返回。

因此，连接池内部维护了若干个Connection实例，如果调用ds.getConnection()，就选择一个空闲连接，并标记它为“正在使用”然后返回，如果对Connection调用close()，那么就把连接再次标记为“空闲”从而等待下次调用。

这样一来，我们就通过连接池维护了少量连接，但可以频繁地执行大量的SQL语句。

通常连接池提供了大量的参数可以配置，例如，维护的最小、最大活动连接数，指定一个连接在空闲一段时间后自动关闭等，需要根据应用程序的负载合理地配置这些参数。

此外，大多数连接池都提供了详细的实时状态以便进行监控。

#### Druid

阿里出品，淘宝和支付宝专用数据库连接池。

但它不仅仅是一个数据库连接池，它还包含一个ProxyDriver，一系列内置的JDBC组件库，一个SQLParser，支持所有JDBC兼容的数据库，包括Oracle、MySQL、Derby、Postgresql、SQL Server、H2等。

Druid针对Oracle和MySQL做了特别优化，比如Oracle的PS Cache内存占用优化，MySQL的Ping检测优化。

Druid提供了MySQL、Oracle、Postgresql、SQL-92的SQL的完整支持，这是一个手写的高性能SQL Parser，支持Visitor模式，使得分析SQL的抽象语法树很方便。

简单SQL语句用时10微秒以内，复杂SQL用时30微秒。

通过Druid提供的SQL Parser可以在JDBC层拦截SQL做相应处理，比如说分库分表、审计等。

Druid防御SQL注入攻击的WallFilter就是通过Druid的SQL Parser分析语义实现的。

#### C3P0

C3P0是一个开放源代码的JDBC连接池，它在lib目录中与Hibernate一起发布，包括了实现JDBC3和JDBC2扩展规范说明的Connection和Statement池的DataSources对象。

#### DBCP

DBCP是一个依赖Jakarta Commons-pool对象池机制的数据库连接池。

DBCP可以直接的在应用程序中使用，Tomcat的数据源使用的就是DBCP。

### HikariCP

HikariCP是一个高性能的JDBC连接池，基于BoneCP做了不少的改进和优化，性能远高于C3P0、Tomcat等连接池，是一个后起之秀，其代码体积也很小，只有130kb。

SpringBoot2默认数据库连接池选择了HikariCP，默认的数据库连接池由Tomcat换成HikariCP。