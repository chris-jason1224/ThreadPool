package com.cj.threadpool.deny;

/**
 * Author:chris - jason
 * Date:2020/3/19.
 * Package:com.cj.threadpool.deny
 */
public class RunnableDenyException extends RuntimeException {
    public RunnableDenyException(String message) {
        super(message);
    }
}
