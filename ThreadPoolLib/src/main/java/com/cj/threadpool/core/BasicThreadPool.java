package com.cj.threadpool.core;

import com.cj.threadpool.RunnableQueue;
import com.cj.threadpool.ThreadFactory;
import com.cj.threadpool.ThreadPool;
import com.cj.threadpool.deny.DenyPolicy;
import com.cj.threadpool.deny.DiscardDenyPolicy;

import java.sql.Statement;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * Author:chris - jason
 * Date:2020/3/19.
 * Package:com.cj.threadpool.core
 * 线程池
 */
public class BasicThreadPool extends Thread implements ThreadPool {

    private final int initSize;//初始化线程数量

    private final int maxSize;//最大线程数量

    private final int coreSize;//核心线程数量

    private int activeCount;//当前活跃线程数量

    private final ThreadFactory threadFactory;//线程创建工厂

    private final RunnableQueue runnableQueue;//任务队列

    private volatile boolean isShutDown = false;//线程池是否被关闭

    private final Queue<ThreadTask> threadQueue = new ArrayDeque<>();//工作线程队列

    public  final static DenyPolicy DEFAULT_DENY_POLICY = new DiscardDenyPolicy();

    private final long keepAliveTime;//自动维护线程池大小的时间周期,单位毫秒

    public BasicThreadPool(int initSize, int maxSize, int coreSize, ThreadFactory threadFactory, int queueSize, DenyPolicy denyPolicy, long keepAliveTime) {
        this.initSize = initSize;
        this.maxSize = maxSize;
        this.coreSize = coreSize;
        this.threadFactory = threadFactory;
        this.runnableQueue = new LinkedRunnableQueue(queueSize, denyPolicy, this);
        this.keepAliveTime = keepAliveTime;
        init();
    }

    private void init() {
        //启动本身这个线程，用于自动维护线程池大小
        start();
        //初始化线程池
        for (int i = 0; i < initSize; i++) {
            newThread();
        }

    }

    //创建一个工作线程
    private void newThread() {
        //InternalTask实现了Runnable接口，通过internalTask创建了线程thread，启动线程thread后，将执行runnable的run（）
        InternalTask internalTask = new InternalTask(runnableQueue);
        Thread thread = this.threadFactory.createThread(internalTask);
        ThreadTask threadTask = new ThreadTask(thread, internalTask);
        threadQueue.offer(threadTask);
        this.activeCount++;
        thread.start();
    }

    //移除一个工作线程
    private void removeThread() {
        ThreadTask threadTask = threadQueue.remove();
        threadTask.getInternalTask().stop();
        this.activeCount--;
    }

    //自动维护线程池大小
    @Override
    public void run() {
        super.run();
        while (!isShutDown && !isInterrupted()) {
            try {
                TimeUnit.MILLISECONDS.sleep(keepAliveTime);
            } catch (Exception e) {
                e.printStackTrace();
                isShutDown = true;
                break;
            }

            synchronized (this) {
                if (isShutDown) {
                    break;
                }

                //initSize <= coreSize <=maxSize

                //1、当前队列中有任务，活跃线程数量小于核心线程数量，线程扩容至核心线程数量
                if (runnableQueue.size() > 0 && activeCount <= coreSize) {
                    for (int i = initSize; i < coreSize; i++) {
                        newThread();
                    }
                    continue;
                }

                //2、当前队列中有任务要处理，活跃线程数大于核心线程数并且小于最大线程数
                if (runnableQueue.size() > 0 && activeCount > coreSize && activeCount < maxSize) {
                    for (int i = coreSize;i<maxSize;i++){
                        newThread();
                    }
                }

                //3、当前队列中没有任务要处理，释放线程，只保留核心线程数
                if(runnableQueue.size() ==0 && activeCount>coreSize){
                    for (int i = coreSize;i< activeCount;i++){
                        removeThread();
                    }
                }

            }

        }
    }

    @Override
    public void execute(Runnable runnable) {
        if (!isShutDown) {
            this.runnableQueue.offer(runnable);
        }
    }

    @Override
    public void shutDown() {
        synchronized (this) {
            if (isShutDown) {
                return;
            }

            isShutDown = true;
            for (ThreadTask threadTask : threadQueue) {
                threadTask.getInternalTask().stop();
                threadTask.getThread().interrupt();
            }

        }
    }

    @Override
    public boolean isShutDown() {
        return this.isShutDown;
    }

    @Override
    public int getInitPoolSize() {
        return initSize;
    }

    @Override
    public int getMaxSize() {
        return maxSize;
    }

    @Override
    public int getCoreSize() {
        return coreSize;
    }

    @Override
    public int getTaskQueueSize() {
        return this.threadQueue.size();
    }

    @Override
    public int getActiveCount() {
        return this.activeCount;
    }
}
