import entities.Configuration;
import entities.PathChromosome;

import java.util.*;

public class GeneticAlgorithm extends Thread  {
    private Configuration configuration;
    private final Random random = new Random();

    public GeneticAlgorithm(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void run() {
        super.run();

        List<PathChromosome> population = getInitialPopulation();
        int t = 2;
        t += 2;
    }

    private List<PathChromosome> getInitialPopulation() {
        List<PathChromosome> population = new ArrayList<>();
        for (int i = 0; i < configuration.getPopulationSize(); i++) {
            List<Integer> genes = new ArrayList<>();
            for (int j = 1; j <= configuration.getChromosomeSize(); j++) {
                genes.add(j);
            }

            for (int j = 0; j < genes.size(); j++) {
                int randomPosition = random.nextInt(genes.size()-1);
                Collections.swap(genes, j, randomPosition);
            }

            population.add(new PathChromosome(genes));
        }
        return  population;
    }
}
