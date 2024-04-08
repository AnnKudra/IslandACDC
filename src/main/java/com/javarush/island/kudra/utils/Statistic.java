package com.javarush.island.kudra.utils;

import com.javarush.island.kudra.abstraction.Organism;
import com.javarush.island.kudra.entity.map.Cell;
import com.javarush.island.kudra.entity.map.GameMap;
import com.javarush.island.kudra.repository.OrganismCreator;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Statistic {
    private final GameMap gameMap;
    @Getter
    private final Map<String, Integer> statistic;
    public Statistic(GameMap gameMap) {
    this.gameMap = gameMap;
    statistic = new HashMap<>();
    }
    private void statisticsUpdate (){
        Map<Class<? extends Organism>, Organism> prototypes = OrganismCreator.getPROTOTYPES();
        prototypes.keySet().
                forEach(organismClass->statistic.put(prototypes.get(organismClass).getIcon(), 0));
    }
    public void displayStatistics(){
        statisticsUpdate();
        Cell[][] cells = gameMap.getCells();
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
}
