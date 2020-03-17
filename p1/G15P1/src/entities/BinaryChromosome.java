package entities;

public class BinaryChromosome extends Chromosome {
	private boolean[] genes;

	public BinaryChromosome() {
	}

	public BinaryChromosome(boolean[] genes) {
		this.genes = genes;
	}

	public boolean[] getGenes() {
		return genes;
	}

	public void setGenes(boolean[] genes) {
		this.genes = genes;
	}

	public BinaryChromosome getCopy() {
		BinaryChromosome chromosome = new BinaryChromosome();
		boolean[] genesCopy = new boolean[genes.length];
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
