package com.javarush.island.kudra.services;

import com.javarush.island.kudra.entity.map.Cell;
import com.javarush.island.kudra.entity.map.GameMap;

public class ReproducingService extends AbstractService {
    private final GameMap gameMap;

    public ReproducingService(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    @Override
    public void run() {
        Cell[][] cells = gameMap.getCells();
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                processOneCell(cell, organism -> organism.reproduce(cell));
            }
        }
    }
}
