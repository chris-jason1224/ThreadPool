package com.cj.threadpool.core;

import com.cj.threadpool.ThreadFactory;
import com.cj.threadpool.ThreadPool;

import java.util.concurrent.TimeUnit;

/**
 * Author:chris - jason
 * Date:2020/3/19.
 * Package:com.cj.threadpool.core
 */
public class Test {
    public static void main(String[] args){

        //线程工厂
        ThreadFactory factory = new ThreadFactory() {
            @Override
            public Thread createThread(Runnable runnable) {
                return new Thread(runnable);
            }
        };

        /**
         * 创建一个线程池
         */
        ThreadPool pool = new BasicThreadPool(3, 10, 5, factory,20,BasicThreadPool.DEFAULT_DENY_POLICY,500);

        for (int i=0;i<100;i++){
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("NAME:"+Thread.currentThread().getName() + "  task +start.....");
                        TimeUnit.MILLISECONDS.sleep(1500);
                        System.out.println("NAME:"+Thread.currentThread().getName() + "  task -done.....");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        e.printStackTrace();
                    }
                }
            });
        }

    }
}
