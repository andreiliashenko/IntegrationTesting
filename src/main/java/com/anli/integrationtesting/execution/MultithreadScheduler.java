package com.anli.integrationtesting.execution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultithreadScheduler {

    protected final ExecutorService executorService;

    public MultithreadScheduler(ExecutorService service) {
        executorService = service;
    }

    public MultithreadScheduler(int threadCount) {
        executorService = Executors.newFixedThreadPool(threadCount);
    }

    public MultithreadScheduler() {
        executorService = Executors.newCachedThreadPool();
    }

    public List executeParallel(List<Callable<Object>> callables) {
        try {
            List<Future<Object>> futures = executorService.invokeAll(callables);
            List results = new ArrayList(futures.size());
            for (Future future : futures) {
                results.add(future.get());
            }
            return results;
        } catch (InterruptedException | ExecutionException ex) {
            throw new RuntimeException(ex);
        }
    }
}
