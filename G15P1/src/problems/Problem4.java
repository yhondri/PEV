package problems;

import base.Utils;
import crossoveralgorithm.CrossoverAlgorithm;
import entities.Chromosome;
import entities.Configuration;
import entities.Solution;
import mutationalgorithm.MutationAlgorithm;
import selection.SelectionAlgorithm;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Problem4 extends Problem {
    double minX1 = 0;
    double maxX = Math.PI;
    private final int geneLength = Utils.getGenotypeLength(minX1, maxX, configuration.getTolerance());

    public Problem4(Configuration configuration, SelectionAlgorithm selectionAlgorithm, CrossoverAlgorithm crossoverAlgorithm, MutationAlgorithm mutationAlgorithm, Delegate delegate) {
        super(configuration, selectionAlgorithm, crossoverAlgorithm, mutationAlgorithm, delegate);
    }

    @Override
    protected Chromosome getRandomChromosome() {
        Random random = new Random();
        Chromosome chromosome = new Chromosome();
        boolean[] chromosomeArray = new boolean[geneLength*configuration.getnValue()];
        for (int i = 0; i < chromosomeArray.length; i++) {
            chromosomeArray[i] = random.nextBoolean();
        }

        chromosome.setGenes(chromosomeArray);

        return chromosome;
    }

    @Override
    protected void sortPopulation(List<Chromosome> population) {
        Collections.sort(population, Collections.reverseOrder());
    }

    public double compareBest(Solution solution, double absBest) {
        return Math.min(solution.getBestFitness(), absBest);
    }

    @Override
    protected double getFitness(Chromosome chromosome) {
        List<Double> phenotypeList = getPhenotype(chromosome.getGenes());
        double result = 0;
        for (int i = 1; i <= configuration.getnValue(); i++) {
            double value = phenotypeList.get(i-1);
            double parentesis = Math.sin(((i+1)*Math.pow(value, 2))/Math.PI);
            result += Math.sin(value)*Math.pow(parentesis, 20);
        }

        return -result;
    }



    @Override
    public String getPhenotypeRepresentation(Chromosome chromosome) {
        List<Double> phenotypeList = getPhenotype(chromosome.getGenes());
        return phenotypeList.toString();
    }

    private List<Double> getPhenotype(boolean[] chromosome) {
        List<Double> phenotype = new ArrayList<>(configuration.getnValue());
        for (int i = 0; i < configuration.getnValue(); i++) {
            double gene;

            if (i == 0) {
                gene = Utils.decodeGene(chromosome, geneLength*i,geneLength-1, true);
            } else {
                gene = Utils.decodeGene(chromosome, ((geneLength*i)-geneLength),geneLength*i, false);
            }

            phenotype.add(gene);
        }
        return phenotype;
    }
}
