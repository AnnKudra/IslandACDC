package com.javarush.island.kudra.abstraction.animal;



public abstract class Predators extends Animal {
    public Predators(String name, String icon, double maxWeight, int maxCount, double maxFood, int maxSpeed) {
        super(name, icon, maxWeight, maxCount, maxFood, maxSpeed);
    }
}
