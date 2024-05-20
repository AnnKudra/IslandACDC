package com.javarush.island.kudra.abstraction.animal;

import com.javarush.island.kudra.abstraction.Organism;
import com.javarush.island.kudra.entity.map.Cell;
import com.javarush.island.kudra.utils.Constants;
import com.javarush.island.kudra.utils.Randomizer;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public abstract class Animal extends Organism {
    @Setter
    private double maxFood;
    @Getter
    @Setter
    private int maxSpeed;
    @Override
    public boolean reproduce(Cell cell) {
        if (isNotHere(cell) || isMaxCountOfOrganismsIn(cell) || !canReproduce(cell))
            return false;
        Organism child;
        try {
            child = this.clone();
            double childWeight = getWeight()/2;
            child.setWeight(childWeight);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        cell.addAsRelocate(child);
        return true;
    }
    private boolean canReproduce(Cell cell) {
        int countOfOrganisms = cell.getOrganismCount(getClass());
        return (countOfOrganisms > Constants.BREEDING_PAIR) && (getWeight() > getMaxWeight()/2);
    }
    @Override
    public boolean eat(Cell cell) {
        if (isNotHere(cell) || !isHungry(cell))
            return false;
        while (!isNotHere(cell) && isHungry(cell)){
        Map.Entry<Organism, Integer> onePreyEntry = findFood(cell);
        if (onePreyEntry == null || !caughtPrey(onePreyEntry))
            return false;
        killAndEat(cell, onePreyEntry);}
        return true;
    }
    private boolean isHungry(Cell cell) {
        cell.getLock().lock();
        try {
        double normalWeight = getMaxWeight()*Constants.NORM_WEIGHT_FACTOR;
    return getWeight() < normalWeight;
    } finally {
        cell.getLock().unlock();
    }
    }
    private Map.Entry<Organism, Integer> findFood(Cell cell){
        cell.getLock().lock();
        try {
            Set<Organism> organismSet = new HashSet<>(cell.getOrganismSet());
            Map<String, Integer> foodTypes = Constants.getFOOD_MAP().get(getName());
            Map<Organism, Integer> prey = new HashMap<>();
            for (Organism organism :
                organismSet) {
            if (foodTypes.containsKey(organism.getName()))
                prey.put(organism, foodTypes.get(organism.getName()));
        }
            return prey.entrySet().stream().findAny().orElse(null);
        }
        finally {
            cell.getLock().unlock();
        }
    }
    private boolean caughtPrey(Map.Entry<Organism, Integer> onePreyEntry) {
        int percentageProbability = onePreyEntry.getValue();
        return Randomizer.get(percentageProbability);
    }
    private void killAndEat(Cell cell, Map.Entry<Organism, Integer> onePreyEntry){
        cell.getLock().lock();
        Organism prey = onePreyEntry.getKey();
        double preyWeight = prey.getWeight();
        try {
            if ((getWeight()+preyWeight) > getMaxWeight())
                setWeight(getMaxWeight());
            else
                setWeight(getWeight()+preyWeight);
        cell.remove(prey);
        }
        finally {
            cell.getLock().unlock();
        }
    }
    @Override
    public boolean move(Cell cell) {
        if (isNotHere(cell))
            return false;
        int speed = Randomizer.getRandom(getMaxSpeed()+1);
        if (speed == 0)
            return false;
        Cell nextCell = cell.findNextCell(cell,speed);
        if (isMaxCountOfOrganismsIn(nextCell) || cell == nextCell)
            return false;
        relocate(nextCell, cell);
        return true;
    }
    private void relocate(Cell nextCell, Cell cell) {
        nextCell.getLock().lock();
        try {
            cell.getLock().lock();
            try {
                nextCell.addAsRelocate(this);
                cell.remove(this);
            }
            finally {
                cell.getLock().unlock();
            }
            double weightLoss = getWeight() * Constants.WEIGHT_LOSS_PERCENT;
            double weight = getWeight()-weightLoss;
            if (weight<=0)
                nextCell.getOrganismSet().remove(this);
            setWeight(weight);
        }
        finally {
            nextCell.getLock().unlock();
        }
    }
}
