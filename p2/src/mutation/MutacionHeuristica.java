package mutation;

import entities.PathChromosome;
import helper.Pair;
import helper.Permutation;
import helper.Utils;
import main.FitnessCalculator;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MutacionHeuristica implements MutationAlgorithm {
    private FitnessCalculator fitnessCalculator;

    @Override
    public PathChromosome mutate(PathChromosome chromosome, double mutationProbability) {
        int firstPosition = 0, secondPosition = 0, thirdPosition = 0;
        List<Integer> permutations = new ArrayList<>();

        /* Obtenemos los 3 valorres a permutar */
        Set<Integer> positions = Utils.pickUniqueRandomList(3, chromosome.getGenes().size() - 1);
        for (Iterator<Integer> integerIterator = positions.iterator(); integerIterator.hasNext(); ) {
            permutations.add(integerIterator.next());
        }

        /* Obtenemos las permutaciones */
        List<int[]> permutationList = new ArrayList<>();
        Permutation.permutation(new int[]{permutations.remove(0), permutations.remove(0), permutations.remove(0)}, 0, permutationList);
        List<PathChromosome> chromosomeList = new ArrayList<>();

        /* Creamos los nuevos individuos con las permutaciones íncluidas */
        for (int i = 0; i < 6; i++) {
            List<Integer> newGenes = new ArrayList<>();
            int index = 0;
            for (int j = 0; j < chromosome.getGenes().size(); j++) {
                if (j == firstPosition || j == secondPosition || j == thirdPosition) {
                    Integer value = permutationList.get(i)[index];
                    newGenes.add(value);
                    index++;
                } else {
                    newGenes.add(chromosome.getGenes().get(j));
                }
            }

            PathChromosome pathChromosome = new PathChromosome(newGenes);
            double fitness = fitnessCalculator.getFitness(pathChromosome);
            pathChromosome.setFitness(fitness);
            chromosomeList.add(pathChromosome);
        }

        Collections.sort(chromosomeList, Collections.reverseOrder());

        //El último es el que tiene mejor fitness.
        return chromosomeList.get(chromosomeList.size()-1);
    }

    public void setFitnessCalculator(FitnessCalculator fitnessCalculator) {
        this.fitnessCalculator = fitnessCalculator;
    }
}
