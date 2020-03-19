package com.cj.threadpool;

/**
 * Author:chris - jason
 * Date:2020/3/19.
 * Package:com.cj.threadpool
 * 线程池接口
 */
public interface ThreadPool {
    /**
     * 提交任务到线程池
     * @param runnable 一个runnable任务
     */
    void execute(Runnable runnable);

    /**
     * 关闭线程池
     */
    void shutDown();

    /**
     * @return 线程池是否被关闭
     */
    boolean isShutDown();

    /**
     * @return 线程池初始化大小
     */
    int getInitPoolSize();

    /**
     * @return 线程池最大线程数量
     */
    int getMaxSize();

    /**
     * @return 线程池核心线程数量
     */
    int getCoreSize();

    /**
     * @return 任务队列大小
     */
    int getTaskQueueSize();

    /**
     * @return 活跃线程数量
     */
    int getActiveCount();

}
