package com.javarush.island.kudra.entity.map;


import com.javarush.island.kudra.abstraction.Organism;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Cell {
    @Getter
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
        if (row<gameMap.getRow()-1){
            availableCells.add(cells[row+1][col]);
        }
        if (col< gameMap.getCol()-1){
            availableCells.add(cells[row][col+1]);
        }
    }
}
