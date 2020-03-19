package com.cj.threadpool;

/**
 * Author:chris - jason
 * Date:2020/3/19.
 * Package:com.cj.threadpool
 * 线程工厂接口
 */
public interface ThreadFactory {
    /**
     * 创建一个线程
     * @param runnable
     * @return
     */
    Thread createThread(Runnable runnable);
}
