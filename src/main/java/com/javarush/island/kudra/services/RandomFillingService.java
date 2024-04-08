package com.javarush.island.kudra.services;

import com.javarush.island.kudra.entity.map.GameMap;

public class RandomFillingService extends AbstractService{
    private final GameMap gameMap;

    public RandomFillingService(GameMap gameMap) {
        this.gameMap = gameMap;
    }
    @Override
    public void run() {
        gameMap.randomFilling();
    }
}
