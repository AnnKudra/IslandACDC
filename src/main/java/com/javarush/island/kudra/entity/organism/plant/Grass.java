package com.javarush.island.kudra.entity.organism.plant;

import com.javarush.island.kudra.abstraction.plant.Plant;
import com.javarush.island.kudra.utils.Config;

@Config(filePath = "kudra/grass.yaml")
public class Grass extends Plant {
    public Grass(String name, String icon, double maxWeight, int maxCount) {
        super(name, icon, maxWeight, maxCount);
    }
}
