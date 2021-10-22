## 公众号(纯洁编程说：chunjie_tech)

## gateway网关

网关的作用是什么？

统一管理微服务请求，权限控制、负载均衡、路由转发、监控、安全控制黑名单和白名单等。

Zuul与Gateway区别：

1、Gateway对比Zuul多依赖了spring-webflux，内部实现了限流、负载均衡等，扩展性也更强，但同时也限制了仅适合于Spring Cloud套件。
   Zuul则可以扩展至其他微服务框架中，其内部没有实现限流、负载均衡等。
　　
2、Zuul仅支持同步不支持异步，Gateway支持同步和异步。

3、Gateway线程开销少，支持各种长连接、websocket，spring官方支持，但运维复杂。
   Zuul编程模型简单，不支持长连接，开发调试运维简单，有线程数限制，延迟堵塞会耗尽线程连接资源。

