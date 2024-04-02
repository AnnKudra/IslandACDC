package com.javarush.island.kudra.abstraction;

import com.javarush.island.kudra.entity.map.Cell;
import com.javarush.island.kudra.interfaces.Eating;
import com.javarush.island.kudra.interfaces.Movable;
import com.javarush.island.kudra.interfaces.Reproducible;
import com.javarush.island.kudra.utils.Randomizer;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Organism implements Cloneable, Reproducible, Eating, Movable {
    public Organism(String name, String icon, double maxWeight, int maxCount) {
        this.name = name;
        this.icon = icon;
        this.maxWeight = maxWeight;
        this.maxCount = maxCount;
    }
    @Getter
    private final String name;
    @Getter
    private final String icon;
    @Getter
    private final double maxWeight;
    @Getter
    private final int maxCount;
    @Getter
    @Setter
    private double weight;

    public Organism clone() throws CloneNotSupportedException {
        Organism clone = (Organism)super.clone();
        double weight = Randomizer.getRandom((clone.maxWeight/2),clone.maxWeight);
        clone.setWeight(weight);
        return clone;
    }
    public boolean eat(Cell cell){
        return false;
    }
    public boolean move(Cell cell){
        return false;
    }
    public boolean reproduce(Cell cell) {
        if (!isHere(cell) || isMaxCountOfOrganismsIn(cell))
            return false;
        List<Cell> availableCells = cell.getAvailableCells();
        int randomIndex = Randomizer.getRandom(availableCells.size());
        Cell nextCell = availableCells.get(randomIndex);
        if (isMaxCountOfOrganismsIn(nextCell))
            return false;
        Organism clone;
        try {
            clone = this.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return addTo(nextCell, clone);
    }

    protected boolean addTo(Cell cell, Organism clone) {
        cell.getLock().lock();
        try {
            Set<Organism> organismSet = cell.getOrganismSet();
            return organismSet.add(clone);
        }
        finally {
            cell.getLock().unlock();
        }
    }

    public boolean isMaxCountOfOrganismsIn(Cell cell) {
        int countOfOrganisms = countOfOrganisms(cell, this.getClass());
        return countOfOrganisms >= this.maxCount;
    }

    protected int countOfOrganisms(Cell cell, Class<? extends Organism> type) {
    cell.getLock().lock();
        try {
            Set<Organism> organismSet = cell.getOrganismSet();
            return organismSet.stream().
                    filter(organism -> organism.getClass().equals(type)).
                    collect(Collectors.toSet()).
                    size();
        } finally {
            cell.getLock().unlock();
        }
    }
    protected boolean isHere(Cell cell){
    cell.getLock().lock();
        try {
            Set<Organism> organismSet = cell.getOrganismSet();
            return organismSet.contains(this);
        } finally {
            cell.getLock().unlock();
        }
    }
}
