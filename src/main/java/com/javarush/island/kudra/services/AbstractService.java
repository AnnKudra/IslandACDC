package com.javarush.island.kudra.services;

import com.javarush.island.kudra.abstraction.Organism;
import com.javarush.island.kudra.entity.map.Cell;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public abstract class AbstractService implements Runnable{
    protected void processOneCell(Cell cell, Consumer<Organism> action){
        Set<Organism> allOrganisms = getSafeSet(cell);
        allOrganisms.forEach(action);
    }
    private Set<Organism> getSafeSet(Cell cell){
        cell.getLock().lock();
        try {
            Set<Organism> organismSet = cell.getOrganismSet();
            return new HashSet<>(organismSet);
        }
        finally {
            cell.getLock().unlock();
        }
    }
}
