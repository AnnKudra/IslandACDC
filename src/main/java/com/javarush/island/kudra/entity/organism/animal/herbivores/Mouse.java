package com.javarush.island.kudra.entity.organism.animal.herbivores;

import com.javarush.island.kudra.abstraction.animal.Herbivores;
import com.javarush.island.kudra.utils.Config;

@Config(filePath = "kudra/herbivores/mouse.yaml")
public class Mouse extends Herbivores {
    public Mouse(String name, String icon, double maxWeight, int maxCount, double maxFood, int maxSpeed) {
        super(name, icon, maxWeight, maxCount, maxFood, maxSpeed);
    }
}
