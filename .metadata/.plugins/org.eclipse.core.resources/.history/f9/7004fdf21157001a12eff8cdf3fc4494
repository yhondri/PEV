import java.util.Random;

public abstract class Cromosoma {

	private boolean[] genes;
	private double fenotipo;
	private double fitness;
	private double puntuacion;
	private double puntuacionAcc;
	private int longitud;
	private double max;
	private double min;
	private boolean maximize;
	
	// Formula de longitud 60 del tema 3
	public void calculaLongitud(double prec) {
		longitud = (int) Math.ceil(Math.log(1 + (max - min)/prec)/Math.log(2));
	}
	
	public abstract void calcFitness();
	
	public void mutaBit(int bit) {
		genes[bit] = !genes[bit];
	}
	
	public abstract Cromosoma[] cruce( Cromosoma padre2, int ptoCruce);
	
	public int compare(Cromosoma rhs) {
		if(rhs == null)return 1;
		int ret;
		if(this.fitness > rhs.fitness)
			ret = 1;
		else if(this.fitness < rhs.fitness)
			ret = -1;
		else 
			ret = 0;
		return ret;
	}
	
	public void randomize(Random rd) {
		for(int i = 0; i < longitud; i++) {
			genes[i] = rd.nextBoolean();
		}
	}
	
	public double getPuntuacion() {
		return puntuacion;
	}
	public void setPuntuacion(double puntuacion) {
		this.puntuacion = puntuacion;
	}
	public boolean[] getGenes() {
		return genes;
	}
	public void setGenes(boolean[] genes) {
		this.genes = genes;
	}
	public double getFitness() {
		return fitness;
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	public double getFenotipo() {
		return fenotipo;
	}
	public void setFenotipo(double fenotipo) {
		this.fenotipo = fenotipo;
	}
	public double getPuntuacionAcc() {
		return puntuacionAcc;
	}
	public void setPuntuacionAcc(double puntuacionAcc) {
		this.puntuacionAcc = puntuacionAcc;
	}
	public int getLongitud() {
		return longitud;
	}
	public void setLongitud(int longitud) {
		this.longitud = longitud;
	}
	public double getMax() {
		return max;
	}
	public void setMax(double max) {
		this.max = max;
	}
	public double getMin() {
		return min;
	}
	public void setMin(double min) {
		this.min = min;
	}

	public boolean isMaximize() {
		return maximize;
	}

	public void setMaximize(boolean maximize) {
		this.maximize = maximize;
	}
}
