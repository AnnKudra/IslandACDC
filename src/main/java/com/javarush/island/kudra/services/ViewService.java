package com.javarush.island.kudra.services;

import com.javarush.island.kudra.entity.map.GameMap;
import java.util.Map;

public class ViewService implements Runnable{
        private final GameMap gameMap;
        public ViewService(GameMap gameMap) {
            this.gameMap = gameMap;
           }

    @Override
    public void run() {
        gameMap.displayStatistics();
        StringBuilder sb = new StringBuilder();
        Map<String, Integer> statisticMap = gameMap.getStatistic();
        for (Map.Entry<String, Integer> entry : statisticMap.entrySet()) {
            sb.append(entry.getKey());
            sb.append(" - ");
            sb.append(entry.getValue());
            sb.append("; ");
            sb.append("\n");
            sb.append("-".repeat(100));
            sb.append("\n");
            System.out.println(sb);
        }

    }
}
