package com.javarush.island.kudra.entity.map;


import com.javarush.island.kudra.abstraction.Organism;
import com.javarush.island.kudra.utils.Randomizer;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@Getter
public class Cell {
    private final List<Cell> availableCells = new ArrayList<>();
    @Getter
    private final Set<Organism> organismSet = new HashSet<>();
    @Getter
    private final Lock lock = new ReentrantLock();

    public void searchAvailableCells(GameMap gameMap, int row, int col){
        Cell[][] cells = gameMap.getCells();
        if (row>0){
            availableCells.add(cells[row-1][col]);
        }
        if (col>0){
            availableCells.add(cells[row][col-1]);
        }
        if (row<gameMap.getRows()-1){
            availableCells.add(cells[row+1][col]);
        }
        if (col< gameMap.getCols()-1){
            availableCells.add(cells[row][col+1]);
        }
    }
    public Cell findNextCell(Cell cell, int speed) {
        Cell nextCell = cell;
        for (int i = 0; i < speed; i++) {
            int countOfAvailableCells = nextCell.getAvailableCells().size();
            int indexOfNextCell = Randomizer.getRandom(countOfAvailableCells);
            nextCell = nextCell.getAvailableCells().get(indexOfNextCell);
        }
        return nextCell;
    }
}
