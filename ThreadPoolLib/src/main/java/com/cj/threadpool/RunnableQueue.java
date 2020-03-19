package com.cj.threadpool;

/**
 * Author:chris - jason
 * Date:2020/3/19.
 * Package:com.cj.threadpool
 * 任务队列接口
 */
public interface RunnableQueue {
    /**
     * 将任务添加至任务队列中
     * @param runnable
     */
    void offer(Runnable runnable);

    /**
     * 从任务列队中取出一个任务
     * @return
     */
    Runnable take();

    /**
     * 获取任务队列中任务数量
     * @return
     */
    int size();
}
