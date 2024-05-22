package com.javarush.island.kudra.entity.map;


import com.javarush.island.kudra.abstraction.Organism;
import com.javarush.island.kudra.utils.Randomizer;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Getter
public class Cell {
    private final List<Cell> availableCells = new ArrayList<>();
    @Getter
    private final Set<Organism> organismSet = new HashSet<>();
    @Getter
    private final Lock lock = new ReentrantLock(true);
    private final Map<Class<? extends Organism>, Integer> organismCounter = new HashMap<>();

    public void addAsReproduce(Organism organism) {
        int countOfOrganismInCell = getOrganismCount(organism.getClass());
        int maxAllowedQuantity = organism.getMaxCount();
        int valueDifference = maxAllowedQuantity - countOfOrganismInCell;
        if (valueDifference > 0) {
            int randomCount = Randomizer.getRandom(valueDifference + 1);
            for (int i = 0; i < randomCount; i++) {
                Organism clone;
                try {
                    clone = organism.clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
                organismSet.add(clone);
                organismCounter.put(organism.getClass(), ++countOfOrganismInCell);
            }
        }
    }

    public void searchAvailableCells(GameMap gameMap, int row, int col) {
        Cell[][] cells = gameMap.getCells();
        if (row > 0) {
            availableCells.add(cells[row - 1][col]);
        }
        if (col > 0) {
            availableCells.add(cells[row][col - 1]);
        }
        if (row < gameMap.getRows() - 1) {
            availableCells.add(cells[row + 1][col]);
        }
        if (col < gameMap.getCols() - 1) {
            availableCells.add(cells[row][col + 1]);
        }
    }

    public Cell findNextCell(Cell cell, int speed) {
        Cell nextCell = cell;
        Cell endCell = cell;
        for (int i = 0; i < speed; i++) {
            int countOfAvailableCells = nextCell.getAvailableCells().size();
            int indexOfNextCell = Randomizer.getRandom(countOfAvailableCells);
            nextCell = nextCell.getAvailableCells().get(indexOfNextCell);
            if (nextCell != cell)
                endCell = nextCell;
        }
        return endCell;
    }

    public int getOrganismCount(Class<? extends Organism> type) {
        return organismCounter.getOrDefault(type, 0);
    }

    public void remove(Organism organism) {
        organismSet.remove(organism);
        Integer currentCount = organismCounter.get(organism.getClass());
        organismCounter.put(organism.getClass(), --currentCount);
    }

    public void addAsRelocate(Organism organism) {
        organismSet.add(organism);
        int currentCount = getOrganismCount(organism.getClass());
        organismCounter.put(organism.getClass(), ++currentCount);
    }

    public void addPlant(Organism organism) {
        int countOfOrganismInCell = getOrganismCount(organism.getClass());
        int maxAllowedQuantity = organism.getMaxCount();
        int valueDifference = maxAllowedQuantity - countOfOrganismInCell;
        if (valueDifference > 0) {
            for (int i = 0; i < valueDifference; i++) {
                Organism clone;
                try {
                    clone = organism.clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
                organismSet.add(clone);
                organismCounter.put(organism.getClass(), ++countOfOrganismInCell);
            }
        }
    }
}
