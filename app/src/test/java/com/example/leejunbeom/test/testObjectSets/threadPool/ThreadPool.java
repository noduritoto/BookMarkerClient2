package com.example.leejunbeom.test.testObjectSets.threadPool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jun on 16. 3. 17..
 */
public class ThreadPool extends ThreadPoolExecutor {
    public ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public Future<?> submit(Runnable runnable) {
        FutureTask futureTask = new FutureTask(runnable, null);
        futureTask.run(); // or queue this future to be run when you want it to be run
        return futureTask;
    }
}
