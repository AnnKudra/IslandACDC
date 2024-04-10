package com.javarush.island.kudra.entity.organism.animal.predators;

import com.javarush.island.kudra.abstraction.animal.Predators;
import com.javarush.island.kudra.utils.Config;

@Config(filePath = "kudra/predators/snake.yaml")
public class Snake extends Predators {
    public Snake(String name, String icon, double maxWeight, int maxCount, double maxFood, int maxSpeed) {
        super(name, icon, maxWeight, maxCount, maxFood, maxSpeed);
    }
}
