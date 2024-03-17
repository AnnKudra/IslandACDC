package com.javarush.island.kudra.abstraction;

import com.javarush.island.kudra.entity.map.Cell;
import com.javarush.island.kudra.interfaces.Eating;
import com.javarush.island.kudra.interfaces.Movable;
import com.javarush.island.kudra.interfaces.Reproducible;

public abstract class Organism implements Cloneable, Reproducible, Eating, Movable {

    public Organism clone() throws CloneNotSupportedException {
        return (Organism) super.clone();
    }
    public boolean eat(Cell cell){
        return false;
    }
    public boolean move(Cell cell){
        return false;
    }
    public boolean reproduce(Cell cell){
        return false;
    }
}
