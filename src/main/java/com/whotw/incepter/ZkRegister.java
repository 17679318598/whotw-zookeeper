package com.whotw.incepter;

import org.apache.zookeeper.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author administrator
 * @description 项目启动前会执行如下run方法
 * @date 2019/9/20 13:33
 */
@Component
public class ZkRegister implements ApplicationRunner {
    @Value("${server.port}")
    private String port;
    //zk url
    private static final String CONNECT_URL = "192.168.1.191:2181";
    //debug设置长一点，否则会报错
    private static final int SESSION_TIMEOUT = 20000;
    public final String rootNode = "/local_server";
    public ZooKeeper zkClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        zkClient = new ZooKeeper(CONNECT_URL, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                try {
                    if (!exists(rootNode)) {
                        zkClient.create(rootNode, rootNode.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                                CreateMode.PERSISTENT);
                    }
                    //注册的是死的URL，根据扩展自己定义
                    if (!exists(rootNode + "/" + port)) {
                        zkClient.create(rootNode + "/" + port, port.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                                CreateMode.EPHEMERAL);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean exists(String node) throws Exception {
        //node if exists
        return zkClient.exists(node, false) != null;
    }
}
