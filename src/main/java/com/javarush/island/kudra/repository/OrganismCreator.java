package com.javarush.island.kudra.repository;



import com.javarush.island.kudra.abstraction.Organism;
import com.javarush.island.kudra.entity.organism.animal.herbivores.Mouse;
import com.javarush.island.kudra.entity.organism.animal.predators.Wolf;
import com.javarush.island.kudra.exception.ApplicationException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OrganismCreator {
    private static final Set<Class<? extends Organism>> TYPES = new HashSet<>();
    private static final Map<Class<? extends Organism>, Organism> PROTOTYPES = new HashMap<>();

    static {
        TYPES.add(Mouse.class);
        TYPES.add(Wolf.class);
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

