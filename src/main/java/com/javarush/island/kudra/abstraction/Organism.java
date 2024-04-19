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

@Getter
public abstract class Organism implements Cloneable, Reproducible, Eating, Movable {
    @Setter
    private String name;
    @Getter
    @Setter
    private String icon;
    @Getter
    @Setter
    private double maxWeight;
    @Getter
    @Setter
    private int maxCount;
    @Getter
    @Setter
    private double weight;

    public Organism clone() throws CloneNotSupportedException {
        Organism clone = (Organism)super.clone();
        double weight = Randomizer.getRandom((clone.maxWeight/2),clone.maxWeight);
        clone.setWeight(weight);
        return clone;
    }
    @Override
    public boolean eat(Cell cell){
        return false;
    }
    @Override
    public boolean move(Cell cell){
        return false;
    }
    @Override
    public boolean reproduce(Cell cell) {
        if (isNotHere(cell))
            return false;
        List<Cell> availableCells = cell.getAvailableCells();
        Organism clone;
        for (Cell availableCell : availableCells) {
            if (isMaxCountOfOrganismsIn(availableCell))
                return false;
            try {
                clone = this.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            return addTo(availableCell, clone);
        }
        return true;
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
    protected boolean isNotHere(Cell cell){
    cell.getLock().lock();
        try {
            Set<Organism> organismSet = cell.getOrganismSet();
            return !organismSet.contains(this);
        } finally {
            cell.getLock().unlock();
        }
    }
}
