package com.javarush.island.kudra.entity.map;

public class GameMap {
    private final Cell[][] cells;

    public GameMap(int rows, int cols) {
        this.cells = new Cell[rows][cols];
    }
}
