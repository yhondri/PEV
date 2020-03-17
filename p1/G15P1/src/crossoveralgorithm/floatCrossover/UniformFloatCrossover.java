package crossoveralgorithm.floatCrossover;

import base.Pair;
import crossoveralgorithm.binaryCrossover.BinaryCrossoverAlgorithm;
import entities.BinaryChromosome;
import entities.FloatChromosome;

import java.util.Random;

public class UniformFloatCrossover implements FloatCrossoverAlgorithm {

    private double prob = 0.5;

    public UniformFloatCrossover(double prob) {
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
                genesA[i] = genesB[i];
                genesB[i] = geneAtI;
            }
        }
        descendants.getElement0().setGenes(genesA);
        descendants.getElement1().setGenes(genesB);
        return descendants;
    }
}
