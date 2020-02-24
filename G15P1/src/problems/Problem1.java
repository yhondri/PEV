package problems;

import base.Utils;
import entities.Chromosome;

import java.util.Random;

public class Problem1 implements GenericProblem {

    double minX1 = -3.0, minX2 = 4.1, maxX1 = 12.1, maxX2 = 5.8, tolerance = 0.001;
    private final int geneALength = Utils.getGenotypeLength(minX1, maxX1, tolerance);
    private final int geneBLength = Utils.getGenotypeLength(minX2, maxX2, tolerance);

    public double getFitness(Chromosome chromosome) {
        double phenotypeA = getPhenotype(chromosome.getGenes(),0,geneALength-1, geneALength, minX1, maxX1, true);
        double phenotypeB = getPhenotype(chromosome.getGenes(), geneALength, (geneALength+geneBLength-1), geneBLength, minX2, maxX2, false);
        //phenotypeA = 11.625;
        //phenotypeB = 5.726;
        //double result = 21.5 + phenotypeA * Math.sin(4 * Math.PI * phenotypeA) + phenotypeB * Math.sin(20 * Math.PI * phenotypeB);
        return 21.5 + phenotypeA * Math.sin(4 * Math.PI * phenotypeA) + phenotypeB * Math.sin(20 * Math.PI * phenotypeB);
    }

    double getPhenotype(boolean[] chromosome, int start, int end, int chromosomeSize, double xmin, double xmax, boolean isChromosomeA) {
        int decimalValue = Utils.decodeGene(chromosome, start, end, isChromosomeA);
        return xmin + (decimalValue * ((xmax - xmin)/(Math.pow(2, chromosomeSize) - 1)));
    }

    @Override
    public Chromosome getRandomChromosome() {
        Chromosome chromosome = new Chromosome();
        boolean[] chromosomeArray = new boolean[geneALength + geneBLength];
        Random random = new Random();

        for (int i = 0; i < chromosomeArray.length; i++) {
            chromosomeArray[i] = random.nextBoolean();
        }

        chromosome.setGenes(chromosomeArray);

        return chromosome;
    }
}
