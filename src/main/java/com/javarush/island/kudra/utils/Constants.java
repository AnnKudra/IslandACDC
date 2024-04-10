package com.javarush.island.kudra.utils;

import com.javarush.island.kudra.abstraction.Organism;
import com.javarush.island.kudra.entity.organism.animal.herbivores.*;
import com.javarush.island.kudra.entity.organism.animal.predators.*;
import com.javarush.island.kudra.entity.organism.plant.Grass;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    private Constants(){
        initFoodMap();
    }
    public static final int ROW = 100;
    public static final int COL = 20;
    public static final int BREEDING_PAIR = 2;
    public static final double NORM_WEIGHT_FACTOR = 0.8;
    public static final double WEIGHT_LOSS_PERCENT = 0.01;
    public static final int FILLING_FREQUENCY = 10;
    public static final int DELAY = 500;
    public static final int COUNT_OF_CORES = Runtime.getRuntime().availableProcessors();

    public static final String[] ORGANISM_NAME = {
            "Wolf",
            //"Snake", "Fox", "Bear", "Eagle", "Horse", "Deer", "Rabbit",
            "Mouse",
            //"Goat", "Sheep", "Boar", "Buffalo", "Duck", "Caterpillar",
            "Grass"};

    public static final Class<? extends Organism>[] ORGANISM_CLASS_NAME = new Class[]{
           Wolf.class, Snake.class, Fox.class, Bear.class, Eagle.class, Horse.class, Deer.class, Rabbit.class,
            Mouse.class, Goat.class, Sheep.class, Boar.class, Buffalo.class, Duck.class, Caterpillar.class, Grass.class};
    public static final int[][] EATING_PROBABILITY = {
        {0, 0, 0, 0, 0, 10, 15, 60, 80, 60, 70, 15, 10, 40, 0, 0},
        {0, 0, 15, 0, 0, 0, 0, 20, 40, 0, 0, 0, 0, 10, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 70, 90, 0, 0, 0, 0, 60, 40, 0},
        {0, 80, 0, 0, 0, 40, 80, 80, 90, 70, 70, 50, 20, 10, 0, 0},
        {0, 0, 10, 0, 0, 0, 0, 90, 90, 0, 0, 0, 0, 80, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 90, 100},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
        {0, 0, 0, 0, 0, 0, 0, 0, 50, 0, 0, 0, 0, 0, 90, 100},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 90, 100},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100}
};
    @Getter
    private static final Map<String, Map<String, Integer>> FOOD_MAP = new HashMap<>();
    private void initFoodMap(){
        for (int i = 0; i < EATING_PROBABILITY.length; i++) {
            String name = ORGANISM_NAME[i];
            Map<String, Integer> dietAsPercentage = new HashMap<>();
            FOOD_MAP.put(name, dietAsPercentage);
            for (int j = 0; j < EATING_PROBABILITY[i].length; j++) {
                int percent = EATING_PROBABILITY[i][j];
                if (percent == 0) {
                    continue;
                }
                String foodName = ORGANISM_NAME[j];
                dietAsPercentage.put(foodName, percent);
            }
        }
    }
}