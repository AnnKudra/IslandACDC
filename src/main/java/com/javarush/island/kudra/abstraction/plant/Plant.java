package com.javarush.island.kudra.abstraction.plant;

import com.javarush.island.kudra.abstraction.Organism;

public abstract class Plant extends Organism {
    public Plant(String name, String icon, double maxWeight, int maxCount) {
        super(name, icon, maxWeight, maxCount);
    }
}
