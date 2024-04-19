package com.javarush.island.kudra.services;

import com.javarush.island.kudra.utils.Constants;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameService extends Thread{
    private final List<Runnable> services;
    public GameService(List<Runnable> services) {
        this.services = services;
    }

    @Override
    public void run() {
        ScheduledExecutorService gameService = Executors.newSingleThreadScheduledExecutor();
        gameService.scheduleWithFixedDelay(this::doOneStep, 0, Constants.DELAY, TimeUnit.MILLISECONDS);
    }

    private void doOneStep() {
        ExecutorService executorService = Executors.newFixedThreadPool(Constants.COUNT_OF_CORES);
        services.forEach(executorService::execute);
        executorService.shutdown();
        try {
            if (executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS));
            System.out.println("-".repeat(155));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    }

