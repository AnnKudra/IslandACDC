package com.javarush.island.kudra;

import com.javarush.island.kudra.entity.map.GameMap;
import com.javarush.island.kudra.services.*;
import com.javarush.island.kudra.utils.Constants;
import java.util.List;

public class ConsoleRunner {
    public static void main(String[] args) {
        GameMap gameMap = new GameMap(Constants.ROW, Constants.COL);
        gameMap.randomFilling();
        List<Runnable> services = List.of(
                new RandomFillingService(gameMap),
                new EatingService(gameMap),
                new ReproducingService(gameMap),
                new MovingService(gameMap),
                new ViewService(gameMap)
        );
        GameService gameService = new GameService(services);
        gameService.start();
    }
}
