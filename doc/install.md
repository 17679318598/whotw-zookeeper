**必须[安装jdk](https://blog.csdn.net/qq_40673786/article/details/100063925)**

下载

    cd /usr/local 
    wget https://mirrors.tuna.tsinghua.edu.cn/apache/zookeeper/zookeeper-3.4.14/zookeeper-3.4.14.tar.gz
    mkdir zk
    tar -zxvf zookeeper-3.4.14.tar.gz -C ./zk

重命名配置文件conf/zoo_sample.cfg

    mv zoo_sample.cfg zoo.cfg

修改配置文件

    tickTime=2000
    dataDir=/var/lib/zookeeper
    clientPort=2181

启动

    bin/zkServer.sh start

本地连接

    bin/zkCli.sh -server 127.0.0.1:2181

简单操作

    ls /
    create /zk_test my_data
    get /zk_test
    set /zk_test junk
    delete /zk_test

