package entities;

public class FloatChromosome extends Chromosome {
    private double[] genes;

    public FloatChromosome() {
    }

    public FloatChromosome(double[] genes) {
        this.genes = genes;
    }

    public double[] getGenes() {
        return genes;
    }

    public void setGenes(double[] genes) {
        this.genes = genes;
    }


    public FloatChromosome getCopy() {
        FloatChromosome chromosome = new FloatChromosome();
        double[] genesCopy = new double[genes.length];
        for (int i = 0; i < genes.length; i++) {
            genesCopy[i] = genes[i];
        }
        chromosome.genes = genesCopy;
        return chromosome;
    }

    @Override
    public int getGenesLength() {
        return genes.length;
    }
}
