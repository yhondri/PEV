package crossoveralgorithm;
import base.*;
import entities.Chromosome;

import java.util.Random;

public class SinglePointCrossover implements CrossoverAlgorithm {
    private Random random;

    public SinglePointCrossover(Random random) {
        this.random = random;
    }

    @Override
    public Pair<Chromosome, Chromosome> crossOver(Chromosome chromosomeA, Chromosome chromosomeB, double crossOverProbability) {
        double randomCrossover = Math.random();
        if (randomCrossover >= crossOverProbability) { //No se produce cruce si el número obtenido es mayor a la probabilidad de cruce.
            return new Pair<>(chromosomeA, chromosomeB);
        }

        int chromosomeSize = Math.max(chromosomeA.getGenesLength(), chromosomeB.getGenesLength());
        int crossoverPoint = random.nextInt( chromosomeSize);

        boolean[] chromosomeANew = new boolean[chromosomeB.getGenesLength()];
        boolean[] chromosomeBNew = new boolean[chromosomeA.getGenesLength()];

        for (int i = 0; i < crossoverPoint; i++) {
            if (i < chromosomeANew.length && i < chromosomeA.getGenesLength()) {
                chromosomeANew[i] = chromosomeA.getGenes()[i];
            }

            if (i < chromosomeBNew.length && i < chromosomeB.getGenesLength()) {
                chromosomeBNew[i] = chromosomeB.getGenes()[i];
            }
        }

        for (int i = crossoverPoint; i < chromosomeSize; i++) {
            if (i < chromosomeANew.length && i < chromosomeB.getGenesLength()) {
                chromosomeANew[i] = chromosomeB.getGenes()[i];
            }

            if (i < chromosomeBNew.length && i < chromosomeA.getGenesLength()) {
                chromosomeBNew[i] = chromosomeA.getGenes()[i];
            }
        }

        return new Pair<>(new Chromosome(chromosomeANew), new Chromosome(chromosomeBNew));
    }
}
