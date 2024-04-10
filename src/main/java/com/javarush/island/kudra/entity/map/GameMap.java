package com.javarush.island.kudra.entity.map;


import com.javarush.island.kudra.abstraction.Organism;
import com.javarush.island.kudra.repository.OrganismCreator;
import com.javarush.island.kudra.utils.Constants;
import com.javarush.island.kudra.utils.Randomizer;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GameMap {
    @Getter
    private final Cell[][] cells;
    private final int rows;
    private final int cols;
    @Getter
    public final Map<String, Integer> statistic;
    public GameMap(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new Cell[rows][cols];
        createMapCells(cells);
        statistic = new HashMap<>();
    }
    private void createMapCells(Cell[][] cells) {
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                cells[row][col] = new Cell();
            }
        }
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                cells[row][col].searchAvailableCells(this, row, col);
            }
        }
    }
    public void randomFilling(){
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (Randomizer.get(Constants.FILLING_FREQUENCY))
                    continue;
                cell.getLock().lock();
                try {
                    Organism organism = getRandomOrganism();
                    Set<Organism> organismSet = cell.getOrganismSet();
                    int countOfOrganismInCell = organismSet.stream().
                            filter(o->o.getClass().equals(organism.getClass())).
                            collect(Collectors.toSet()).
                            size();
                    int maxAllowedQuantity =organism.getMaxCount();
                    int randomAddedCount = Randomizer.getRandom(1, maxAllowedQuantity - countOfOrganismInCell);
                    for (int i = 0; i < randomAddedCount; i++) {
                        Organism clone;
                        try {
                            clone = organism.clone();
                        } catch (CloneNotSupportedException e) {
                            throw new RuntimeException(e);
                        }
                        organismSet.add(clone);
                    }
                }
                finally {
                    cell.getLock().unlock();
                }
            }
        }
    }

    private Organism getRandomOrganism() {
        int countOrganismTypes = OrganismCreator.getTYPES().size();
        int indexOfType = Randomizer.getRandom(countOrganismTypes);
        Class<? extends Organism> searchedType = Constants.ORGANISM_CLASS_NAME[indexOfType];
        return OrganismCreator.getPrototype(searchedType);
    }
    private void statisticsUpdate (){
        Map<Class<? extends Organism>, Organism> prototypes = OrganismCreator.getPROTOTYPES();
        prototypes.keySet().
                forEach(organismClass->statistic.put(prototypes.get(organismClass).getIcon(), 0));
    }
    public void displayStatistics(){
        statisticsUpdate();
        Cell[][] cells = getCells();
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                cell.getLock().lock();
                try {
                    Set<Organism> organismSet = cell.getOrganismSet();
                    organismSet.forEach(organism -> {
                        String organismIcon = organism.getIcon();
                        int amountOfOrganisms = statistic.get(organismIcon);
                        statistic.put(organismIcon, ++amountOfOrganisms);
                    });
                }
                finally {
                    cell.getLock().unlock();
                }
            }
        }
    }
    public int getRow(){
        return rows;
    }
    public int getCol(){
        return cols;
    }
}