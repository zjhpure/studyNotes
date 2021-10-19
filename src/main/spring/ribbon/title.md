## 公众号(纯洁编程说：chunjie_tech)

## ribbon负载均衡

负载均衡策略：

1、RoundRobinRule轮询算法，如果有A、B两个实例，那么该算法的逻辑是选择A，再选择B，再选择A，轮询下去。

2、RandomRule随机算法，在服务列表中随机选取。

3、BestAvailableRule选择最小并发法，选择一个最小的并发请求server。
如果有A、B两个实例，当A有4个请求正在处理中，B有2个请求正在处理中，下次选择的时候会选择B，因为B处理的数量是最少的，认为它压力最小，这种场景适合于服务所在机器的配置都相同的情况下，否则不太适用。

4、WeightedResponseTimeRule算法，权重响应法。
原理是根据请求的响应时间计算权重，如果响应时间越长，那么对应的权重越低，权重越低的服务器，被选择的可能性就越低。
在某些场景下，服务器的性能不一致，如果采用轮询算法或随机算法的话，无法充分的利用服务器的资源，某些服务器处理的快，应该多分配一些请求，某些服务器处理的慢，就可以少分配一些请求。
