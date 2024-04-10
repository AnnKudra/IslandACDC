package com.javarush.island.kudra.abstraction.animal;



public abstract class Herbivores extends Animal{
    public Herbivores(String name, String icon, double maxWeight, int maxCount, double maxFood, int maxSpeed) {
        super(name, icon, maxWeight, maxCount, maxFood, maxSpeed);
    }

}
