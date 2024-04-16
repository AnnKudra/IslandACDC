package com.javarush.island.kudra.repository;



import com.javarush.island.kudra.abstraction.Organism;
import com.javarush.island.kudra.entity.organism.animal.herbivores.*;
import com.javarush.island.kudra.entity.organism.animal.predators.*;
import com.javarush.island.kudra.entity.organism.plant.Grass;
import com.javarush.island.kudra.exception.ApplicationException;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OrganismCreator {
    @Getter
    private static final Set<Class<? extends Organism>> TYPES = new HashSet<>();
    @Getter
    private static final Map<Class<? extends Organism>, Organism> PROTOTYPES = new HashMap<>();

    static {
        TYPES.add(Mouse.class);
        TYPES.add(Wolf.class);
        TYPES.add(Grass.class);
        TYPES.add(Bear.class);
        TYPES.add(Boar.class);
        TYPES.add(Buffalo.class);
        TYPES.add(Caterpillar.class);
        TYPES.add(Deer.class);
        TYPES.add(Duck.class);
        TYPES.add(Goat.class);
        TYPES.add(Horse.class);
        TYPES.add(Rabbit.class);
        TYPES.add(Sheep.class);
        TYPES.add(Eagle.class);
        TYPES.add(Fox.class);
        TYPES.add(Snake.class);
        fillingWithPrototypes();
    }
    private static void fillingWithPrototypes(){
        for (Class<? extends Organism> type :
                TYPES) {
            Organism organism = ConfigLoader.createOrganism(type);
            PROTOTYPES.put(type, organism);
        }
    }
    public static Organism getPrototype(Class<? extends Organism> type){
        if (!PROTOTYPES.containsKey(type)){
            throw new ApplicationException(String.format("Type: %s not found", type.getSimpleName()));
        }
        return PROTOTYPES.get(type);
    }
}

