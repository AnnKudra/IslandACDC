package com.javarush.island.kudra;

import com.javarush.island.kudra.entity.map.Cell;
import com.javarush.island.kudra.entity.map.GameMap;
import com.javarush.island.kudra.services.*;
import com.javarush.island.kudra.utils.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsoleRunner {
    public static void main(String[] args) {
        GameMap gameMap = new GameMap(Constants.ROW, Constants.COL);
        Cell[][] cells = gameMap.getCells();
        Map<String, Integer> statistic = new HashMap<>();
        RandomFillingService.randomFilling(gameMap.getCells());
        List<Runnable> services = List.of(
                //new ViewService(statistic, cells),
                //new RandomFillingService(cells),
                //new ReproducingService(gameMap),
                //new EatingService(gameMap),
                new MovingService(gameMap),
                new ViewService(statistic, cells)
        );
        GameService gameService = new GameService(services);
        gameService.start();
    }
}
