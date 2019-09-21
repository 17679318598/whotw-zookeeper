package com.whotw.distributed_lock;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author whotw
 * @description OrderService
 * @date 2019/9/21 22:09
 */
public class OrderService implements Runnable {
    @Override
    public void run() {
        Lock lock = new ZkDistributedLock();
        try {
            lock.lock();
            //运到zk连接超时一定要手动回滚事务
            System.out.println("--------------订单号---" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + OrderGen.gen());
        } finally {
            lock.unLock();
        }
    }
}
