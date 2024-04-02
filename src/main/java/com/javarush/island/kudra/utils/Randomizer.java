package com.javarush.island.kudra.utils;

import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {
    public static int getRandom(int max) {
        return ThreadLocalRandom.current().nextInt(max);
    }
    public static int getRandom(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
    public static double getRandom(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }
    public static boolean get(int percentageProbability) {
        return getRandom(0, 100) < percentageProbability;
    }
}
