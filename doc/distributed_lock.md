* 单jvm的线程安全
1. 何时产生线程安全问题

共享变量

2. 解决方案

syncronized 自动党

lock        手动党

cas

* 分布式的线程安全【不同的jvm中】，即多jvm

1. 解决方案

Redis、Redison分布式锁

zk、curator分布式锁

* zk实现分布式锁

原理 : 临时[顺序]节点+事件通知
测试: ccom.whotw.distributed_lock.TestLock
1. 临时节点+加事件通知实现分布式锁【com.whotw.distributed_lock.OrderService >> Lock lock = new ZkDistributedLock()】
2. 持久节点+临时顺序节点+事件通知【com.whotw.distributed_lock.OrderService >> Lock lock = new ZkDistributedLock2()】

