package com.javarush.island.kudra.entity.map;

import lombok.Getter;

@Getter
public class GameMap {
    private final Cell[][] cells;
    @Getter
    private final int rows;
    @Getter
    private final int cols;

    public GameMap(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new Cell[rows][cols];
        createMapCells(cells);
    }

    private void createMapCells(Cell[][] cells) {
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                cells[row][col] = new Cell();
            }
        }
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                cells[row][col].searchAvailableCells(this, row, col);
            }
        }
    }
}