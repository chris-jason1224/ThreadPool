package com.cj.threadpool.deny;

import com.cj.threadpool.ThreadPool;

/**
 * Author:chris - jason
 * Date:2020/3/19.
 * Package:com.cj.threadpool.deny
 * 直接丢弃任务策略
 */
public class DiscardDenyPolicy implements DenyPolicy{
    @Override
    public void reject(Runnable runnable, ThreadPool threadPool) {
        //do nothing,just deny it
    }
}
