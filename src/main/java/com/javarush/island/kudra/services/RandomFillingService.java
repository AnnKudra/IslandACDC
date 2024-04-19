package com.javarush.island.kudra.services;

import com.javarush.island.kudra.abstraction.Organism;
import com.javarush.island.kudra.entity.map.Cell;
import com.javarush.island.kudra.repository.OrganismCreator;
import com.javarush.island.kudra.utils.Constants;
import com.javarush.island.kudra.utils.Randomizer;

import java.util.Set;
import java.util.stream.Collectors;

public class RandomFillingService extends AbstractService {
    private final Cell[][] cells;

    public RandomFillingService(Cell[][] cells) {
        this.cells = cells;
    }

    @Override
    public void run() {
        randomFilling(cells);

    }
    public static void randomFilling(Cell[][] cells){
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (!Randomizer.get(Constants.FILLING_FREQUENCY))
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
                    if (maxAllowedQuantity <= countOfOrganismInCell)
                        continue;
                    int valueDifference = maxAllowedQuantity - countOfOrganismInCell;
                    int randomCount = 1+ Randomizer.getRandom(valueDifference);
                    for (int i = 0; i < randomCount; i++) {
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

    private static Organism getRandomOrganism() {
        int countOrganismTypes = OrganismCreator.getTYPES().size();
        int indexOfType = Randomizer.getRandom(countOrganismTypes);
        Class<? extends Organism> searchedType = Constants.ORGANISM_CLASS_NAME[indexOfType];
        return OrganismCreator.getPrototype(searchedType);
    }
}