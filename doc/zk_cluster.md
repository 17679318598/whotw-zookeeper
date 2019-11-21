*请先查看doc/install.md单台服务器的搭建*
+ 可用节点【启动的服务器数】> 总服务器数/2 才能保证zk集群正常运行
+  zk节点数最好为奇数台

    根据1的算法，如果总的为3台最多只能宕机一台，如果总的为4台最多也只能宕机一台，所以奇数可以节约服务器资源

+  zk集群原理采用过半机制【即某台服务器A得到过半服务器的投票，剩下的服务器不用投票，A就是Leader】

+ 集群配置

    1. 每台zk服务器conf/zoo.cfg配置如下
    ````
    server.1=zk1_ip:2888:3888
    server.2=zk2_ip:2888:3888
    server.3=zk3_ip:2888:3888
    #server.3=zk3_ip:2888:3888:observer   ##observer类型，不可参与选举
    ````
    2. 每台zk服务器配置myid文件 
    ````
    dataDir=/var/lib/zookeeper[zoo.cfg]   ###myid文件目录
    /var/lib/zookeeper删除该目录下所有内容后创建myid文件
    每台zk服务器的myid内容为zoo.cfg对应server.x 的x值
    
    ````