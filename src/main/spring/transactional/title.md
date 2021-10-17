## 公众号(纯洁编程说：chunjie_tech)

## transaction事务

事务的4个特性(ACID)：

原子性(Atomicity)、一致性(Consistency)、隔离性(Isolation)、持久性(Durability)

原子性：

事务是一个完整的操作。事务内的各元素是不可分割的。事务中的元素必须作为一个整体提交或回滚。如果事务中的任何元素失败，整个事务将失败。

一致性：

在事务开始前，数据必须处于一致状态；在事务结束后，数据的状态也必须保持一致。通过事务对数据所做的修改不能损坏数据。

隔离性：

事务的执行不受其他事务的干扰，事务执行的中间结果对其他事务必须是透明的。

持久性：

对于提交的事务，系统必须保证事务对数据库的改变不被丢失，即使数据库发生故障。

Spring事务：

在目前的业务开发过程中，多以Spring框架为主，Spring支持两种方式的事务管理编程式事务和声明式事务。

编程式事务：

```
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Override
    public Map<String, Object> saveResource(MultipartFile file) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            // 相关业务
            
            // 手动提交
            transactionManager.commit(status);
        } catch (Exception e) {
            log.error("Exception:{}", ExceptionUtil.stacktraceToString(e));
            // 发生异常时进行回滚
            transactionManager.rollback(status);
        }
    }
```

声明式事务：

```
    @Transactional
    @Override
    public Map<String, Object> saveResource(MultipartFile file) {
        // 相关业务
    }
```

标有注解的方法通过AOP完成事务的回滚与提交。

@Transactional注解失效的场景：

1、@Transactional写到在非public的方法上

2、@Transactional注解属性propagation设置错误，默认属性是REQUIRED，如果设置PROPAGATION_SUPPORTS、PROPAGATION_NOT_SUPPORTED、PROPAGATION_NEVER，那么事务将不会发生回滚。

3、@Transactional注解属性rollbackFor设置错误，例如指定了异常，但是抛出的异常不是指定的。

4、同一个类中方法调用，导致@Transactional失效，例如调用类中的方法，而这个方法用@Transactional注解，这是无效的。

5、异常被你的catch“吃了”导致@Transactional失效

6、数据库引擎不支持事务

事务使用：

1、我们可以使用@Transactional(readOnly = true)来设置只读事务，在将事务设置成只读后，当前只读事务就不能进行写的操作，否则报错。

2、若一个事务里只发出一条select语句，则没有必要启用事务支持。

3、若一个事务里先后发出了多条select语句，启用事务支持，能够防止多次查询到的数据不一致(维持可重复读)，而且有一定的优化，比如两次的查询只发出一条sql。

4、除了单条查询语句，其他情况都使用事务。

