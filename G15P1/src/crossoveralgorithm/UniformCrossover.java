package crossoveralgorithm;

import base.Pair;
import entities.Chromosome;

import java.util.Random;

public class UniformCrossover implements CrossoverAlgorithm {

    private double prob = 0.5;

    public UniformCrossover(double prob) {
        this.prob = prob;
    }

    @Override
    public Pair<Chromosome, Chromosome> crossOver(Chromosome chromosomeA, Chromosome chromosomeB) {
        Random rng = new Random();
        Pair<Chromosome, Chromosome> descendants = new Pair<>(chromosomeA.getCopy(), chromosomeB.getCopy());
        boolean[] genesA = chromosomeA.getGenes().clone(), genesB = chromosomeB.getGenes().clone();
        boolean geneAtI;
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
