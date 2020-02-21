import utils.ByteOps;

public class CromosomaHolder extends Cromosoma {

	public CromosomaHolder(double prec) {
		setMin(-10);
		setMax(10);
		setMaximize(false);
		super.calculaLongitud(prec);
		setLongitud(getLongitud()*2);
		setGenes(new boolean[getLongitud()]);
	}
	
	public CromosomaHolder(Cromosoma c) {
		setMin(-10);
		setMax(10);
		setMaximize(false);
		setLongitud(c.getLongitud());
		setGenes(c.getGenes().clone());
	}
	
	public double[] decode() {
		boolean spl[][] = ByteOps.splitBitStream(getGenes());
		return new double[]{
				ByteOps.parseBitStream(spl[0], getMin(), getMax(), getLongitud()/2),
				ByteOps.parseBitStream(spl[1], getMin(), getMax(), getLongitud()/2)};
	}

	@Override
	public void calcFitness() {
		double parsedVal[] = decode();
		setFenotipo(calcHolder(parsedVal[0], parsedVal[1]));
		setFitness(-getFenotipo());
	}
	
	public static double calcHolder(double x, double y) {
		return -Math.abs(Math.sin(x)
				*Math.cos(y)
				*Math.exp(Math.abs(1 - (Math.sqrt(x*x + y*y)/Math.PI))));
	}

	@Override
	public Cromosoma[] cruce(Cromosoma padre2, int ptoCruce)  {
		CromosomaHolder[] ret = new CromosomaHolder[2];
		ret[0] = new CromosomaHolder(this);
		ret[1] = new CromosomaHolder(padre2);
		boolean[] aux1 = this.getGenes(), aux2 = padre2.getGenes();
		for(int i = 0; i < ptoCruce; i++) {
			aux2[i] = this.getGenes()[i];
			aux1[i] = padre2.getGenes()[i];
		}
		ret[0].setGenes(aux1);
		ret[1].setGenes(aux2);
		return ret;
	}

}
