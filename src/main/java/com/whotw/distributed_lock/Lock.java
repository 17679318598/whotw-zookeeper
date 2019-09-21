package com.whotw.distributed_lock;

/**
 * @author whotw
 * @description Lock
 * @date 2019/9/21 21:36
 */
public interface Lock {
    public void  lock();

    public void unLock();
}

