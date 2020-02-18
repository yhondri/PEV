package crossoveralgorithm;
import base.*;

import java.util.Random;

public class SinglePointCrossover implements CrossoverAlgorithm {
    private Random random;

    public SinglePointCrossover(Random random) {
        this.random = random;
    }

    @Override
    public Pair<boolean[], boolean[]> crossOver(boolean[] chromosomeA, boolean[] chromosomeB, double crossOverProbability) {
        double randomCrossover = Math.random();
        if (randomCrossover >= crossOverProbability) { //No se produce cruce si el número obtenido es mayor a la probabilidad de cruce.
            return new Pair<>(chromosomeA, chromosomeB);
        }

        int chromosomeSize = Math.max(chromosomeA.length, chromosomeB.length);
        int crossoverPoint = random.nextInt( chromosomeSize);

        boolean[] chromosomeANew = new boolean[chromosomeB.length];
        boolean[] chromosomeBNew = new boolean[chromosomeA.length];

        for (int i = 0; i < crossoverPoint; i++) {
            if (i < chromosomeANew.length && i < chromosomeA.length) {
                chromosomeANew[i] = chromosomeA[i];
            }

            if (i < chromosomeBNew.length && i < chromosomeB.length) {
                chromosomeBNew[i] = chromosomeB[i];
            }
        }

        for (int i = crossoverPoint; i < chromosomeSize; i++) {
            if (i < chromosomeANew.length && i < chromosomeB.length) {
                chromosomeANew[i] = chromosomeB[i];
            }

            if (i < chromosomeBNew.length && i < chromosomeA.length) {
                chromosomeBNew[i] = chromosomeA[i];
            }
        }

        return new Pair<>(chromosomeANew, chromosomeBNew);
    }
}
