package com.whotw.distributed_lock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 *  临时节点+加事件通知实现分布式锁
 */
public class ZkDistributedLock extends AbstractLock {
    private static final String CONNECTION = "192.168.1.191:2181";
    private static final int TIMEOUT = 20000;
    private ZkClient zkClient = new ZkClient(CONNECTION, TIMEOUT);
    private static final String LOCK = "/lock";
    private CountDownLatch countDownLatch = null;

    @Override
    public void unLock() {
        if (zkClient != null) {
            //连接关闭删除临时节点
            zkClient.close();
            super.unLock();
        }
    }


    @Override
    boolean tryLock() {
        try {
            //获取锁
            zkClient.createEphemeral(LOCK);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    @Override
    void waitLock() {
        IZkDataListener listener = new IZkDataListener() {
            // zk事件监听节点修改
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
            }

            //zk事件监听节点删除
            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println("--------------节点删除" + s);
                if (countDownLatch != null) {
                    // 计数器为0的情况，await 后面的继续执行,释放阻塞线程
                    countDownLatch.countDown();
                }
            }
        };
        if (countDownLatch == null) {
            countDownLatch = new CountDownLatch(1);
        }
        try {
            //阻塞其他线程，下面的代码不执行
            countDownLatch.wait();
        } catch (Exception e) {
            // TODO
        }
    }

}
