package com.javarush.island.kudra.services;

import com.javarush.island.kudra.entity.map.Cell;
import com.javarush.island.kudra.entity.map.GameMap;

public class MovingService extends AbstractService{
    private final GameMap gameMap;

    public MovingService(GameMap gameMap) {
        this.gameMap = gameMap;
    }
    @Override
    public void run() {
        long start = System.currentTimeMillis();
        Cell[][] cells = gameMap.getCells();
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                processOneCell(cell, organism -> organism.move(cell));
            }
        }
        System.out.println("MovingService: " + (System.currentTimeMillis() - start)/1000);
    }
}
