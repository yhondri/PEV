package mutationalgorithm;

import entities.Chromosome;

public class BasicMutation implements MutationAlgorithm {
    @Override
    public Chromosome mutate(boolean[] genes, double mutationProbability) {
        Chromosome newChromosome = new Chromosome();
        newChromosome.setGenesLength(genes.length);

        boolean[] newGenes = new boolean[newChromosome.getGenesLength()];
        double range = mutationProbability - 0 + 1;

        for (int i = 0; i < newChromosome.getGenesLength(); i++) {
            double result = Math.random() * range;
            newGenes[i] = (result < mutationProbability) ? !genes[i] : genes[i];
        }

        newChromosome.setGenes(newGenes);
        return newChromosome;
    }
}
