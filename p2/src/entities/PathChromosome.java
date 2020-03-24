package entities;

import java.util.ArrayList;
import java.util.List;

public class PathChromosome implements Comparable<PathChromosome> {
    private List<Integer> genes;
    private double phenotype;
    private double fitness;
    private double puntuation;
    private int genesLength;
    private double grade;
    private double acumulatedGrade;
    //Probabilidad de selección
    private double acumulatedFitness;

    public PathChromosome(List<Integer> genes) {
        this.genes = genes;
    }

    public List<Integer> getGenes() {
        return genes;
    }

    public void setGenes(List<Integer> genes) {
        this.genes = genes;
    }

    /**
     * Java es "pass by value" lo que quiere decir que cuando pasamos un
     * elemento a un método estamos pasando la dirección de memoria.
     * Es imprescindible utilizar este método si queremos crear un elemento nuevo (llamémoslo B)
     * utilizando el genotipo de otro elemento (A) ya que de lo contrario si copiamos directamente el geenotipo (de A)
     * al manipularlo en el nuevo objeto estaremos cambiádolo en el actual, es decir, lo que cambiemos en B cambiará en A.
     * Imprescindible para Mutaciones
     * */
    public List<Integer> cloneGenotype() {
        return new ArrayList<>(genes);
    }

    public void setAcumulatedFitness(double acumulatedFitness) {
        this.acumulatedFitness = acumulatedFitness;
    }

    public double getAcumulatedFitness() {
        return acumulatedFitness;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public PathChromosome getCopy() {
        List<Integer> genesCopy = new ArrayList<>();
        for (int i = 0; i < genes.size(); i++) {
            genesCopy.add(genes.get(i));
        }
        return new PathChromosome(genesCopy);
    }

    @Override
    public int compareTo(PathChromosome obj) {
        return Double.compare(this.fitness, obj.getFitness());
    }
}
