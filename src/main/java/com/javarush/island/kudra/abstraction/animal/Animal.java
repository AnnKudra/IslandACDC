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
    Organism previousOrganism = new Organism() {};

    @Override
    public boolean reproduce(Cell cell) {
        if (isNotHere(cell) || isMaxCountOfOrganismsIn(cell) || !canReproduce(cell))
            return false;
        Organism child;
        try {
            child = this.clone();
            double childWeight = getWeight() / 2;
            child.setWeight(childWeight);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        cell.addAsRelocate(child);
        return true;
    }

    private boolean canReproduce(Cell cell) {
        int countOfOrganisms = cell.getOrganismCount(getClass());
        return (countOfOrganisms > Constants.BREEDING_PAIR) && (getWeight() > getMaxWeight() / 2);
    }

    @Override
    public boolean eat(Cell cell) {
        if (previousOrganism.getClass().equals(this.getClass()))
            return false;
        if (isNotHere(cell) || !isHungry())
            return false;
        while (!isNotHere(cell) && isHungry()) {
            if (findFood(cell).isEmpty()) {
                previousOrganism = this;
                return false;
            }
            Map.Entry<Organism, Integer> onePreyEntry = findFood(cell).entrySet().stream().findAny().orElse(null);
            if (onePreyEntry == null || !caughtPrey(onePreyEntry))
                return false;
            killAndEat(cell, onePreyEntry);
        }
        return true;
    }

    private boolean isHungry() {
        double normalWeight = getMaxWeight() * Constants.NORM_WEIGHT_FACTOR;
        return getWeight() < normalWeight;
    }

    private Map <Organism, Integer> findFood(Cell cell) {
        Set<Organism> organismSet = new HashSet<>(cell.getOrganismSet());
        Map<String, Integer> foodTypes = Constants.getFOOD_MAP().get(getName());
        Map<Organism, Integer> prey = new HashMap<>();
        for (Organism organism :
                organismSet) {
            if (foodTypes.containsKey(organism.getName()))
                prey.put(organism, foodTypes.get(organism.getName()));
        }
        return prey;
    }

    private boolean caughtPrey(Map.Entry<Organism, Integer> onePreyEntry) {
        int percentageProbability = onePreyEntry.getValue();
        return Randomizer.get(percentageProbability);
    }

    private void killAndEat(Cell cell, Map.Entry<Organism, Integer> onePreyEntry) {
        Organism prey = onePreyEntry.getKey();
        double preyWeight = prey.getWeight();
        if ((getWeight() + preyWeight) > getMaxWeight())
            setWeight(getMaxWeight());
        else
            setWeight(getWeight() + preyWeight);
        cell.remove(prey);
    }

    @Override
    public boolean move(Cell cell) {
        if (isNotHere(cell))
            return false;
        int speed = Randomizer.getRandom(getMaxSpeed() + 1);
        if (speed == 0)
            return false;
        Cell nextCell = cell.findNextCell(cell, speed);
        if (nextCell.getLock().tryLock()) {
            try {
                if (isMaxCountOfOrganismsIn(nextCell) || cell == nextCell)
                    return false;
                relocate(nextCell, cell);
            } finally {
                nextCell.getLock().unlock();
            }
        }
        return true;
    }

    private void relocate(Cell nextCell, Cell cell) {
        nextCell.addAsRelocate(this);
        cell.remove(this);
        double weightLoss = getWeight() * Constants.WEIGHT_LOSS_PERCENT;
        double weight = getWeight() - weightLoss;
        if (weight <= 0)
            nextCell.remove(this);
        setWeight(weight);
    }
}
