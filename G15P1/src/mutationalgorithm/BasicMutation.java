package mutationalgorithm;

import entities.Chromosome;

import java.util.Random;

public class BasicMutation implements MutationAlgorithm {
    @Override
    public Chromosome mutate(Chromosome chromosome, double mutationProbability) {
        boolean[] newGenes = new boolean[chromosome.getGenesLength()];
        Random random = new Random();
        for (int i = 0; i < chromosome.getGenesLength(); i++) {
            double result = random.nextDouble();
            newGenes[i] = (result < mutationProbability) ? !chromosome.getGenes()[i] : chromosome.getGenes()[i];
        }

        chromosome.setGenes(newGenes);
        return chromosome;
    }
}
