
public class Cromosoma {

	private boolean[] genes;
	private double fenotipo;
	private double fitness;
	private double puntuacion;
	private double puntuacionAcc;
	private int longitud;
	
	
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
}
