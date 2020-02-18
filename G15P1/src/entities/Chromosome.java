package entities;

public class Chromosome {
    private boolean[] genes;
    private double phenotype;
    private double fitness;
    private double puntuation;
    private int genesLength;

    public boolean[] getGenes() {
        return genes;
    }

    public void setGenes(boolean[] genes) {
        this.genes = genes;
    }

    public double getPhenotype() {
        return phenotype;
    }

    public void setPhenotype(double phenotype) {
        this.phenotype = phenotype;
    }

    public double getFitness() {
        return fitness;
    }

    public double getPuntuation() {
        return puntuation;
    }

    public void setPuntuation(double puntuation) {
        this.puntuation = puntuation;
    }

    public int getGenesLength() {
        return genesLength;
    }

    public void setGenesLength(int genesLength) {
        this.genesLength = genesLength;
    }
}
