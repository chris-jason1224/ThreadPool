package com.cj.threadpool.deny;

import com.cj.threadpool.ThreadPool;

/**
 * Author:chris - jason
 * Date:2020/3/19.
 * Package:com.cj.threadpool.deny
 * 抛出异常拒绝策略
 */
public class AbortDenyPolicy implements DenyPolicy {
    @Override
    public void reject(Runnable runnable, ThreadPool threadPool) {
        throw new RunnableDenyException(runnable + "will be abort..");
    }
}
