package com.javarush.island.kudra.abstraction.plant;

import com.javarush.island.kudra.abstraction.Organism;
import com.javarush.island.kudra.entity.map.Cell;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class Plant extends Organism {
    @Override
    public void addTo(Cell cell, Organism organism) {
        Set<Organism> organismSet = cell.getOrganismSet();
        int countOfOrganismInCell = organismSet.stream().
                filter(o->o.getClass().equals(organism.getClass())).
                collect(Collectors.toSet()).
                size();
        int maxAllowedQuantity =organism.getMaxCount();
        int valueDifference = maxAllowedQuantity - countOfOrganismInCell;
        if (maxAllowedQuantity <= countOfOrganismInCell)
            valueDifference = 0;
        for (int i = 0; i < valueDifference; i++) {
            Organism clone;
            try {
                clone = organism.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            organismSet.add(clone);
        }
    }
}
