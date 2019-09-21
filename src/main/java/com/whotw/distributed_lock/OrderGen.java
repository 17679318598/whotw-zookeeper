package com.whotw.distributed_lock;

import java.util.Date;

/**
 * @author whotw
 * @description OrderGen
 * @date 2019/9/21 22:07
 */
public class OrderGen {
    private static int count;

    public static int gen() {
        return ++count;
    }
}
