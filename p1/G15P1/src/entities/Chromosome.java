package entities;

public abstract class Chromosome implements Comparable<Chromosome> {
	private double phenotype;
	private double fitness;
	private double puntuation;
	private int genesLength;
	private double grade;
	private double acumulatedGrade;
	//Probabilidad de selección
	private double acumulatedFitness;

	public double getPhenotype() {
		return phenotype;
	}

	public void setPhenotype(double phenotype) {
		this.phenotype = phenotype;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public double getPuntuation() {
		return puntuation;
	}

	public void setPuntuation(double puntuation) {
		this.puntuation = puntuation;
	}

	public abstract int getGenesLength();

	public void setGenesLength(int genesLength) {
		this.genesLength = genesLength;
	}

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	public double getAcumulatedGrade() {
		return acumulatedGrade;
	}

	public void setAcumulatedGrade(double acumulatedGrade) {
		this.acumulatedGrade = acumulatedGrade;
	}

	public double getAcumulatedFitness() {
		return acumulatedFitness;
	}

	public void setAcumulatedFitness(double acumulatedFitness) {
		this.acumulatedFitness = acumulatedFitness;
	}

	@Override
	public int compareTo(Chromosome obj) {
		return Double.compare(this.getFitness(), obj.getFitness());
	}
}
