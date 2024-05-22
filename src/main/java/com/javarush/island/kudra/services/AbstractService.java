package com.javarush.island.kudra.services;

import com.javarush.island.kudra.abstraction.Organism;
import com.javarush.island.kudra.entity.map.Cell;

import java.util.HashSet;
import java.util.Set;

import java.util.concurrent.locks.Lock;
import java.util.function.Consumer;

public abstract class AbstractService implements Runnable {
    protected void processOneCell(Cell cell, Consumer<Organism> action) {
        Lock lock = cell.getLock();
        if (lock.tryLock())
            try {
                Set<Organism> allOrganisms = getSafeSet(cell);
                allOrganisms.forEach(action);
            } finally {
                cell.getLock().unlock();
            }
    }

    public Set<Organism> getSafeSet(Cell cell) {
        Set<Organism> organismSet = cell.getOrganismSet();
        return new HashSet<>(organismSet);
    }
}
