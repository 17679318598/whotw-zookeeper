package com.whotw;

import org.apache.zookeeper.*;
import org.junit.Before;
import org.junit.Test;

public class TestZkClient {
    //zk url
    private static final String CONNECT_URL = "192.168.1.191:2181";
    //debug设置长一点，否则会报错
    private static final int SESSION_TIMEOUT = 20000;
    private static ZooKeeper zkClient = null;
    private final String rootNode = "/whotw";
    private final String childNode1 = rootNode + "/" + "childNode1";
    private final String childNode2 = rootNode + "/" + "childNode2";
    private static final String[] SERVER_NODE = {"127.0.0.1:8080", "127.0.0.1:8081"};

    @Before
    public void init() throws Exception {
        zkClient = new ZooKeeper(CONNECT_URL, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                //收到事件通知后的回调函数（应该是我们自己的事件处理逻辑）
                System.out.println(event.getType() + "----" + event.getPath());
                try {
                    if (!exists(rootNode)) {
                        zkClient.create(rootNode, "data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                                CreateMode.PERSISTENT);
                    }
                    for (String childNode : SERVER_NODE) {
                        //服务注册
                        if (!exists(rootNode + "/" + childNode)) {
                            zkClient.create(rootNode + "/" + childNode, childNode.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                                    CreateMode.EPHEMERAL);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Test
    public void TestZk() throws Exception {
        //create persistent node
        String exists = exists(rootNode) ? print(rootNode) : zkClient.create(rootNode, rootNode.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        System.out.println("---------------打印创建的根节点");
        System.out.println(exists);
        //get node
        byte[] data = zkClient.getData(rootNode, false, null);
        System.out.println("---------------打印创建的根节点数据");
        System.out.println(new String(data, "utf-8"));
        //create ephemeral children node
        String exists1 = exists(childNode1) ? print(childNode1) : zkClient.create(childNode1, "I am son1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL);
        System.out.println("---------------打印创建的子节点");
        System.out.println(exists1);
        String exists2 = exists(childNode2) ? print(childNode2) : zkClient.create(childNode2, "I am son2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL);
        System.out.println("---------------打印创建的子节点");
        System.out.println(exists2);
        //get children node [java8+] print child info
        System.out.println("---------------打印子节点");
        zkClient.getChildren(rootNode, true).forEach(System.out::println);
        //set node data
        zkClient.setData(childNode1, "I am son1 after update".getBytes(), -1);
        byte[] afterSon1 = zkClient.getData(childNode1, false, null);
        System.out.println(new String(afterSon1, "utf-8"));
        System.out.println("---------------修改子节点数据后，打印子节点");
        zkClient.getChildren(rootNode, true).forEach(System.out::println);
        // -1表示所有版本
        zkClient.delete(childNode2, -1);
        System.out.println("---------------删除子节点数据后，打印子节点");
        zkClient.getChildren(rootNode, true).forEach(System.out::println);
    }

    private boolean exists(String node) throws Exception {
        //node if exists
        return zkClient.exists(node, false) != null;
    }

    private String print(String node) throws Exception {
        return node + "节点已存在";
    }

}