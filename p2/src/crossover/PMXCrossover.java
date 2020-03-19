package crossover;

import entities.PathChromosome;
import helper.Pair;

import java.util.*;

public class PMXCrossover implements CrossoverAlgorithm {
    @Override
    public Pair<PathChromosome, PathChromosome> crossOver(PathChromosome chromosomeA, PathChromosome chromosomeB) {
        Random random = new Random();
        Pair<PathChromosome, PathChromosome> descendants = new Pair<>(chromosomeA.getCopy(), chromosomeB.getCopy());
        List<Integer> genesA = new ArrayList<>(Collections.nCopies(chromosomeA.getGenes().size(), chromosomeA.getGenes().size()+1));
        List<Integer> genesB = new ArrayList<>(Collections.nCopies(chromosomeA.getGenes().size(), chromosomeA.getGenes().size()+1));

        int initialPoint = 0;
        int endPoint = 0;

        while (initialPoint >= endPoint) {
            initialPoint = random.nextInt(genesA.size()-1);
            endPoint = random.nextInt(genesA.size()-1);
        }

        List<Integer> homologosA = new ArrayList<>();
        List<Integer> homologosB = new ArrayList<>();
        //1º Intercambiamos los genes del segmento seleccionado
        for (int i = initialPoint; i < endPoint; i++) {
            genesA.set(i, chromosomeB.getGenes().get(i));
            homologosA.add(chromosomeA.getGenes().get(i));
            genesB.set(i, chromosomeA.getGenes().get(i));
            homologosB.add(chromosomeB.getGenes().get(i));
        }

        for (int i = 0; i < chromosomeA.getGenes().size(); i++) {
            //En este punto no intercambiamos los genes del segmento seleccionado anteriormente
            if (i >= initialPoint && i < endPoint) {
                continue;
            }

            if (genesA.contains(chromosomeA.getGenes().get(i))) {
                for (int j = 0; j < homologosA.size(); j++) {
                    if (!genesA.contains(homologosA.get(j))) {
                        genesA.set(i, homologosA.get(j));
                    }
                }
            } else {
                genesA.set(i, chromosomeA.getGenes().get(i));
            }

            if (genesB.contains(chromosomeB.getGenes().get(i))) {
                for (int j = 0; j < homologosB.size(); j++) {
                    if (!genesB.contains(homologosB.get(j))) {
                        genesB.set(i, homologosB.get(j));
                    }
                }
            } else {
                genesB.set(i, chromosomeB.getGenes().get(i));
            }
        }

        return descendants;
    }
}
