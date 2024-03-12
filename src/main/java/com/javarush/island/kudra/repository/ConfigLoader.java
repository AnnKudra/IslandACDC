package com.javarush.island.kudra.repository;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.javarush.island.kudra.abstraction.Organism;
import com.javarush.island.kudra.exception.ApplicationException;
import com.javarush.island.kudra.utils.Config;

import java.net.URL;

public class ConfigLoader {
    public static Organism createOrganism(Class<? extends Organism> type){
        if (!type.isAnnotationPresent(Config.class)){
            throw new ApplicationException(String.format("Class %s must have Config annotation", type.getSimpleName()));
        }
        URL filePath = getConfigFilePath(type);
        return loadObject(filePath, type);
    }

    public static URL getConfigFilePath(Class<? extends Organism> type) {
        Config config = type.getAnnotation(Config.class);
        return type.getClassLoader().getResource(config.filePath());
    }
    public static Organism loadObject(URL filePath, Class<? extends Organism> type){
        YAMLMapper mapper = new YAMLMapper();
        Organism organism;
        try {
            organism = mapper.readValue(filePath,type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return organism;
    }
}
