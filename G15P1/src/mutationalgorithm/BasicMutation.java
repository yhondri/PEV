package mutationalgorithm;

import entities.Chromosome;

public class BasicMutation implements MutationAlgorithm {
    @Override
    public Chromosome mutate(Chromosome chromosome, double mutationProbability) {

        boolean[] newGenes = new boolean[chromosome.getGenesLength()];
        double range = mutationProbability - 0 + 1;

        for (int i = 0; i < chromosome.getGenesLength(); i++) {
            double result = Math.random() * range;
            newGenes[i] = (result < mutationProbability) ? !chromosome.getGenes()[i] : chromosome.getGenes()[i];
        }

        chromosome.setGenes(newGenes);
        return chromosome;
    }
}
