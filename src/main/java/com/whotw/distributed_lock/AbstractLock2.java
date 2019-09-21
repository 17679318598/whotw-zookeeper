package com.whotw.distributed_lock;

public abstract class AbstractLock2 implements Lock {

    @Override
    public void lock() {
        if (tryLock()) {
            System.out.println("#####" + Thread.currentThread().getName() + "获取锁成功######");
        } else {
            //一个拿到其他等待
            waitLock();
        }
    }

    @Override
    public void unLock() {
        System.out.println("#####\" + Thread.currentThread().getName() + \"释放锁成功######");
    }

    /**
     * 获取锁失败进行等待
     */
    abstract void waitLock();

    /**
     * 获取锁
     */
    abstract boolean tryLock();
}
