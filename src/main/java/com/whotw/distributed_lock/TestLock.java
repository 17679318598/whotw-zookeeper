package com.whotw.distributed_lock;

/**
 * @author whotw
 * @description TestLock
 * @date 2019/9/21 21:53
 */
public class TestLock {
    public static void main(String[] args) {
        //new OrderService();放外面表示单jvm【单例】，里面表示多jvm【多例】
        for (int i = 0; i < 10; i++) {
            new Thread(new OrderService()).start();
        }
    }
}
