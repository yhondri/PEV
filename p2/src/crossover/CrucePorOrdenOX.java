package crossover;

import entities.PathChromosome;
import helper.Pair;
import helper.Utils;
import mutation.MutacionPorInsercion;

import java.util.*;

public class CrucePorOrdenOX implements CrossoverAlgorithm {
    @Override
    public Pair<PathChromosome, PathChromosome> crossOver(PathChromosome chromosomeA, PathChromosome chromosomeB) {
        List<Integer> genesA = new ArrayList<>(Collections.nCopies(chromosomeA.getGenes().size(), chromosomeA.getGenes().size()+1));
        List<Integer> genesB = new ArrayList<>(Collections.nCopies(chromosomeA.getGenes().size(), chromosomeA.getGenes().size()+1));
        Queue<Integer> queueGenesA = new LinkedList<>();
        Queue<Integer> queueGenesB = new LinkedList<>();

        int initialPoint = 0;
        int endPoint = 0;

        /**
         * 1º Elegimos puntos de cortes o segmento a mutar.
         */
        while (initialPoint >= endPoint) {
            initialPoint = Utils.getRandom(chromosomeA.getGenes().size()-1, 0);
            endPoint = Utils.getRandom(chromosomeA.getGenes().size()-1, 0);
        }

        /**
         * 2 Insertamos parte final de los genes del padre
         */
        for (int i = endPoint+1; i < genesA.size(); i++) {
            queueGenesA.add(chromosomeA.getGenes().get(i));
            queueGenesB.add(chromosomeB.getGenes().get(i));
        }

        /**
         * 2.1 Insertamos resto de elementos de los genes del padre.
         */
        for (int i = 0; i <= endPoint && i < genesA.size(); i++) {
            queueGenesA.add(chromosomeA.getGenes().get(i));
            queueGenesB.add(chromosomeB.getGenes().get(i));
        }

        /**
         * 2.2 Insertamos los genes de los padres en los hijos en el segmento seleccionado.
         * Quitamos de la lista de elementos a insertar más adelante, los elementos que vamos insertando.
         */
        for (int i = initialPoint; i <= endPoint && i < genesA.size(); i++) {
            genesA.set(i, chromosomeB.getGenes().get(i));
            genesB.set(i, chromosomeA.getGenes().get(i));
            queueGenesA.remove(genesA.get(i));
            queueGenesB.remove(genesB.get(i));
        }

        /**
         * 3 Insertamos el resto de genes.
         */

        /**
         * 3.1 Insertamos genes finales (los que quedan después del segmento de intercambio de genes entre padres).
         */

        for (int i = endPoint+1; i < genesA.size() && !queueGenesA.isEmpty() && !queueGenesB.isEmpty(); i++) {
            //En este punto no intercambiamos los genes del segmento seleccionado anteriormente
            if (i >= initialPoint && i < endPoint) {
                continue;
            }

            Integer city = queueGenesA.poll();
            genesA.set(i, city);
            city = queueGenesB.poll();
            genesB.set(i, city);
        }


        /**
         * 3.2 Insertamos genes iniciales (los que van antes del segmento de intercambio de genes entre padres).
         */
        for (int i = 0; i < initialPoint && !queueGenesA.isEmpty() && !queueGenesB.isEmpty(); i++) {
            //En este punto no intercambiamos los genes ya que es el segmento donde hemos intercambiado los genes de los padres.
            if (i >= initialPoint && i < endPoint) {
                continue;
            }

            Integer city = queueGenesA.poll();
            genesA.set(i, city);

            city = queueGenesB.poll();
            genesB.set(i, city);
        }

        return new Pair<>(new PathChromosome(genesA), new PathChromosome(genesB));
    }
}
