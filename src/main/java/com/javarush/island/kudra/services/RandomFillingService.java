package com.javarush.island.kudra.services;

import com.javarush.island.kudra.entity.map.GameMap;
import com.javarush.island.kudra.utils.Constants;
import com.javarush.island.kudra.utils.Randomizer;

public class RandomFillingService extends AbstractService{
    private final GameMap gameMap;

    public RandomFillingService(GameMap gameMap) {
        this.gameMap = gameMap;
    }
    @Override
    public void run() {
        if (Randomizer.get(Constants.FILLING_FREQUENCY)){
        gameMap.randomFilling();}
    }
}
