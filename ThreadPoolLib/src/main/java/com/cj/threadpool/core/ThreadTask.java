package com.cj.threadpool.core;

/**
 * Author:chris - jason
 * Date:2020/3/19.
 * Package:com.cj.threadpool.core
 * Thread和InternalTask包装类
 */
public class ThreadTask {
    private Thread thread;
    private InternalTask internalTask;

    public ThreadTask(Thread thread, InternalTask internalTask) {
        this.thread = thread;
        this.internalTask = internalTask;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public InternalTask getInternalTask() {
        return internalTask;
    }

    public void setInternalTask(InternalTask internalTask) {
        this.internalTask = internalTask;
    }
}
