package crossoveralgorithm.floatCrossover;

import base.Pair;
import entities.FloatChromosome;

import java.util.Random;

public class ArithmeticFloatCrossover implements FloatCrossoverAlgorithm {

    private double prob = 0.5;

    public ArithmeticFloatCrossover(double prob) {
        this.prob = prob;
    }

    @Override
    public Pair<FloatChromosome, FloatChromosome> crossOver(FloatChromosome chromosomeA, FloatChromosome chromosomeB) {
        Random rng = new Random();
        Pair<FloatChromosome, FloatChromosome> descendants = new Pair<>(chromosomeA.getCopy(), chromosomeB.getCopy());
        double[] genesA = chromosomeA.getGenes().clone(), genesB = chromosomeB.getGenes().clone();
        double geneAtI;
        for (int i = 0; i < chromosomeA.getGenesLength(); i++) {
            if (rng.nextDouble() < prob) {
                geneAtI = genesA[i];
                genesA[i] = (genesB[i] + geneAtI) / 2;
                genesB[i] = (genesB[i] + geneAtI) / 2;
            }
        }
        descendants.getElement0().setGenes(genesA);
        descendants.getElement1().setGenes(genesB);
        return descendants;
    }
}
