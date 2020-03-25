package main;

import entities.PathChromosome;

public class FitnessCalculator {

    private int[][] flujoMatrix;
    private int[][] distanciaMatrix;

    public FitnessCalculator(int[][] flujoMatrix, int[][] distanciaMatrix) {
        this.flujoMatrix = flujoMatrix;
        this.distanciaMatrix = distanciaMatrix;
    }

    public double getFitness(PathChromosome chromosome) {
        double fitnes = 0;
        for (int i = 0; i < chromosome.getGenes().size(); i++) {
            for (int j = 0; j < chromosome.getGenes().size(); j++) {
                fitnes += distanciaMatrix[i][j] * flujoMatrix[chromosome.getGenes().get(i)][chromosome.getGenes().get(j)];
            }
        }

        return fitnes;
    }
}
