package mutation;

import entities.PathChromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BasicMutationAlgorithm implements MutationAlgorithm {
    @Override
    public PathChromosome mutate(PathChromosome chromosome, double mutationProbability) {
        List<Integer> newGenes = new ArrayList<>();
        Random random = new Random();
//        for (int i = 0; i < chromosome.getGenes().size(); i++) {
//            double result = random.nextDouble();
//            newGenes.set(i, (result < mutationProbability) ? !chromosome.getGenes().get(i) : chromosome.getGenes().get(i);
//        }
//
//        chromosome.setGenes(newGenes);
        return chromosome;
    }
}
