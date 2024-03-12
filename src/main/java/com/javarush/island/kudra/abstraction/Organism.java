package com.javarush.island.kudra.abstraction;

import com.javarush.island.kudra.interfaces.Reproducible;

public abstract class Organism implements Cloneable, Reproducible {

    public Organism clone() throws CloneNotSupportedException {
        Organism clone = (Organism) super.clone();
        return clone;
    }
}
