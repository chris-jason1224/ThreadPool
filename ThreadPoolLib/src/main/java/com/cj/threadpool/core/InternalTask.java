package com.cj.threadpool.core;

import com.cj.threadpool.RunnableQueue;

/**
 * Author:chris - jason
 * Date:2020/3/19.
 * Package:com.cj.threadpool.core
 * 线程池核心消费任务
 */
public class InternalTask implements Runnable {

    private final RunnableQueue runnableQueue;//任务队列

    private volatile boolean running = true;//执行开关

    public InternalTask(RunnableQueue runnableQueue) {
        this.runnableQueue = runnableQueue;
    }

    @Override
    public void run() {
        while(running && !Thread.currentThread().isInterrupted()){
            try {
                //不断的从任务队列中取出一个任务来执行
                Runnable task = runnableQueue.take();
                task.run();
            }catch (Exception ex){
                ex.printStackTrace();
                running = false;
                break;
            }
        }
    }

    //停止任务
    void stop(){
        this.running = false;
    }
}
