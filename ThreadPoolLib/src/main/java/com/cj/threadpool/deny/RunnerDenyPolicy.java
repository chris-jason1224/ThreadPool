package com.cj.threadpool.deny;

import com.cj.threadpool.ThreadPool;

/**
 * Author:chris - jason
 * Date:2020/3/19.
 * Package:com.cj.threadpool.deny
 * 任务提交者线程执行任务 拒绝策略
 */
public class RunnerDenyPolicy implements DenyPolicy {
    @Override
    public void reject(Runnable runnable, ThreadPool threadPool) {
        if(!threadPool.isShutDown()){
            //runnable任务的提交者所在线程直接执行任务
            runnable.run();
        }
    }
}
