## 公众号(纯洁编程说：chunjie_tech)

## 使用分布式的原因

优点：
1、易于开发：开发方式简单，运行调试容易。
2、易于测试。
3、易于部署。
4、模块重用度高。
5、系统扩展性更高。
6、加强系统可用性：通过分布式架构来冗余系统以消除单点故障，从而提高系统的可用性。
7、团队协作流程改善。
8、系统容量上限理论上可以无限大。

缺点：
1、架构设计变得复杂，尤其是其中的分布式事务。
2、测试麻烦。
3、部署麻烦，维护麻烦。
4、管理分布式系统中的服务和调度变得困难和复杂。
5、服务的调用增大了响应的时间。

其实，一般来说，分布式的缺点反过来就是单体架构的优点，单体架构的缺点反过来也是分布式的优点。

最重要的是分布式可以理论上的无限扩大系统容量，单体架构无法做到，所以现在几乎都在用分布式，因为数据量是累计上涨的。