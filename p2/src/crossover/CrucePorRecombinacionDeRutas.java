package crossover;

import entities.PathChromosome;
import helper.Pair;
import java.util.*;

public class CrucePorRecombinacionDeRutas implements CrossoverAlgorithm {
    @Override
    public Pair<PathChromosome, PathChromosome> crossOver(PathChromosome chromosomeA, PathChromosome chromosomeB) {
        Map<Integer, List<Integer>> ciudadesVecinas = new HashMap<>();
        for (int i = 0; i < chromosomeA.getGenes().size(); i++) {
            ciudadesVecinas.put(i, new ArrayList<>());
        }

        ciudadesVecinas = createMatrizDeVecinos(chromosomeA.getGenes(), ciudadesVecinas);
        ciudadesVecinas = createMatrizDeVecinos(chromosomeB.getGenes(), ciudadesVecinas);

        /*En caso de no encontrar una solución válida, devolvemos los cromosomas con los genes del padre.*/
        List<Integer> genesA = getGenes(ciudadesVecinas, chromosomeB.getGenes(), chromosomeA.getGenes());
        if (genesA.size() != chromosomeA.getGenes().size()) {
            genesA = chromosomeA.getGenes();
        }

        List<Integer> genesB = getGenes(ciudadesVecinas, chromosomeA.getGenes(), chromosomeB.getGenes());
        if (genesB.size() != chromosomeB.getGenes().size()) {
            genesB = chromosomeB.getGenes();
        }

        return new Pair<>(new PathChromosome(genesA), new PathChromosome(genesB));
    }

    private Map<Integer, List<Integer>> createMatrizDeVecinos(List<Integer> genes, Map<Integer, List<Integer>> ciudadesVecinas) {
        for (int i = 0; i < genes.size(); i++) {
            /* Caso especial, si i == 0, solo tenemos que mirar al siguiente. */
            if (i == 0 && !ciudadesVecinas.get(genes.get(i)).contains(genes.get(i + 1))) {
                ciudadesVecinas.get(genes.get(i)).add(genes.get(i + 1));
            } else if (i != 0 && !ciudadesVecinas.get(genes.get(i)).contains(genes.get(i - 1))) {
                ciudadesVecinas.get(genes.get(i)).add(genes.get(i - 1));
            }

            /* Caso especial, si i == genes.size(), solo tenemos que mirar al anterior. */
            if (i + 1 < genes.size() && !ciudadesVecinas.get(genes.get(i)).contains(genes.get(i + 1))) {
                ciudadesVecinas.get(genes.get(i)).add(genes.get(i + 1));
            }
        }

        return ciudadesVecinas;
    }

    private List<Integer> getGenes(Map<Integer, List<Integer>> ciudadesVecinas, List<Integer> parentA, List<Integer> parentB) {
        List<Integer> newGenes = null;
        List<Integer> parents = new ArrayList<>();
        for (int i = 0; i <parentA.size(); i++) {
            parents.add(parentA.get(i));
        }

        for (int i = 0; i <parentA.size(); i++) {
            parents.add(parentB.get(i));
        }

        boolean didCompleteGenes = false;

        while(!parents.isEmpty() && !didCompleteGenes) {
            newGenes = new ArrayList<>();
            Integer nextValue = parents.get(0); //Si se produce bloqueo probamos con el siguiente valor inicial.
            newGenes.add(nextValue);
            parents.remove(0);

            boolean exit = false;
            while ((newGenes.size() != parentA.size()) && !exit) {
                PriorityQueue<Neighbor> vecinoPriorityQueue = new PriorityQueue<>();
                List<Integer> neighborsList = ciudadesVecinas.get(nextValue);
                for (Integer neighbor : neighborsList) {
                    Neighbor newNeighbor = new Neighbor(neighbor, ciudadesVecinas.get(neighbor).size());
                    vecinoPriorityQueue.add(newNeighbor);
                }

                boolean didFindNeighbor = false;
                while (!didFindNeighbor && !vecinoPriorityQueue.isEmpty()) {
                    Neighbor neighbor = vecinoPriorityQueue.poll();
                    Integer newNeighbor = neighbor.value;
                    if (!newGenes.contains(newNeighbor)) {
                        newGenes.add(newNeighbor);
                        didFindNeighbor = true;
                        nextValue = newNeighbor;
                    }
                }

                if (!didFindNeighbor) { //Se produce un bloque ne el momento que entre los vecinos no hemos encontrado ningún valor válido.
                    exit = true;
                }

                if (newGenes.size() == parentA.size()) {
                    didCompleteGenes = true;
                }
            }
        }

        return newGenes;
    }

    class Neighbor implements Comparable<Neighbor> {
        Integer value;
        int numbOfNeighbors;

        public Neighbor(Integer value, int numbOfNeighbors) {
            this.value = value;
            this.numbOfNeighbors = numbOfNeighbors;
        }


        @Override
        public int compareTo(Neighbor o) {
            return Integer.compare(numbOfNeighbors, o.numbOfNeighbors);
        }
    }
}
