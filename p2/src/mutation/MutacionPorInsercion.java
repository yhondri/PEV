package mutation;

import entities.PathChromosome;
import helper.Utils;

import java.util.*;

/**
 * Se elige un número de ciudades al azar y una posición también al azar para las nuevas ciudades.
 * Luego se insertarn las ciudades en las nuevas posiciones.
 */
public class MutacionPorInsercion implements MutationAlgorithm {

    class NewCity implements Comparable<NewCity>  {
        int position;
        Integer city;

        @Override
        public int compareTo(NewCity o) {
            return Integer.compare(city, o.city);
        }
    }

    @Override
    public PathChromosome mutate(PathChromosome chromosome, double mutationProbability) {
        /**
         * Elegimos el número de ciudades que van a cambiar su posición.
         */
        int numberOfCities = Utils.getRandom(chromosome.getGenes().size()-1, 0);
        Queue<NewCity> queue = new LinkedList<>();
        List<Integer> newGenes = chromosome.cloneGenotype();

        /**
         * Elegimos las ciudades y sus posiciones.
         */
        while (queue.size() != numberOfCities) {
            NewCity newCity = new NewCity();
            newCity.position = Utils.getRandom(chromosome.getGenes().size()-1, 0);
            newCity.city = Utils.getRandom(chromosome.getGenes().size()-1, 0);
            if (!queue.contains(newCity)) {
                queue.add(newCity);
            }
        }

        /**
         * Primero eliminamos la ciudad del genotipo actual y a continuación la insertamos en la nueva posición elegida.
         */
        while (!queue.isEmpty()) {
            NewCity newCity = queue.poll();
            newGenes.remove(newCity.city);
            newGenes.add(newCity.position, newCity.city);
        }

        return new PathChromosome(newGenes);
    }
}