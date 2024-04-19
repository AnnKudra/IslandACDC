package com.javarush.island.kudra.services;

import com.javarush.island.kudra.abstraction.Organism;
import com.javarush.island.kudra.entity.map.Cell;
import com.javarush.island.kudra.repository.OrganismCreator;

import java.util.Map;
import java.util.Set;

public class ViewService implements Runnable{
    private final Map<String, Integer> statistic;
    private final Cell[][] cells;
    public ViewService(Map<String, Integer> statistic, Cell[][] cells) {
        this.statistic = statistic;
        this.cells = cells;
        }
    @Override
    public void run() {
        displayStatistics(cells);
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : statistic.entrySet()) {
            sb.append(entry.getKey());
            sb.append("-");
            sb.append(entry.getValue());
            sb.append(" ");
            }
        System.out.println(sb);
        }
    private void statisticsUpdate (){
        Map<Class<? extends Organism>, Organism> prototypes = OrganismCreator.getPROTOTYPES();
        prototypes.keySet().
                forEach(organismClass->statistic.put(prototypes.get(organismClass).getIcon(), 0));
    }
    public void displayStatistics(Cell[][] cells){
        statisticsUpdate();
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
