package com.javarush.island.kudra.services;

import com.javarush.island.kudra.entity.map.Cell;
import com.javarush.island.kudra.entity.map.GameMap;

public class EatingService extends AbstractService{
    private final GameMap gameMap;

    public EatingService(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        Cell[][] cells = gameMap.getCells();
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                processOneCell(cell, organism -> organism.eat(cell));
            }
        }
        System.out.println("EatingService: " + (System.currentTimeMillis() - start)/1000);
    }
}
