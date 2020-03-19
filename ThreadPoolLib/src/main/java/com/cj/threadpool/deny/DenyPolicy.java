package com.cj.threadpool.deny;

import com.cj.threadpool.ThreadPool;

/**
 * Author:chris - jason
 * Date:2020/3/19.
 * Package:com.cj.threadpool
 * 拒绝策略接口
 */
public interface DenyPolicy {
    void reject(Runnable runnable, ThreadPool threadPool);
}
