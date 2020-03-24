package crossover;

import entities.PathChromosome;
import helper.Pair;
import helper.Utils;
import java.util.*;

public class PMXCrossover implements CrossoverAlgorithm {
    @Override
    public Pair<PathChromosome, PathChromosome> crossOver(PathChromosome chromosomeA, PathChromosome chromosomeB) {
        List<Integer> genesA = new ArrayList<>(Collections.nCopies(chromosomeA.getGenes().size(), null));
        List<Integer> genesB = new ArrayList<>(Collections.nCopies(chromosomeA.getGenes().size(), null));

        Set<Integer> positions = Utils.pickUniqueRandomList(2, chromosomeA.getGenes().size() - 1);
        Iterator<Integer> integerIterator =  positions.iterator();
        int temp1, temp2;
        temp1 = integerIterator.next();
        temp2 = integerIterator.next();
        int initialPoint =  Math.min(temp1, temp2);
        int endPoint = Math.max(temp1, temp2);

        List<Integer> homologosA = new ArrayList<>();
        List<Integer> homologosB = new ArrayList<>();
        //1º Intercambiamos los genes del segmento seleccionado
        for (int i = initialPoint; i < endPoint; i++) {
            genesA.set(i, chromosomeB.getGenes().get(i));
            genesB.set(i, chromosomeA.getGenes().get(i));
            homologosA.add(chromosomeA.getGenes().get(i));
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
                        break;
                    }
                }
            } else {
                genesA.set(i, chromosomeA.getGenes().get(i));
            }

            if (genesB.contains(chromosomeB.getGenes().get(i))) {
                for (int j = 0; j < homologosB.size(); j++) {
                    if (!genesB.contains(homologosB.get(j))) {
                        genesB.set(i, homologosB.get(j));
                        break;
                    }
                }
            } else {
                genesB.set(i, chromosomeB.getGenes().get(i));
            }
        }

        return new Pair<>(new PathChromosome(genesA), new PathChromosome(genesB));
    }
}
