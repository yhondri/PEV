import java.util.Random;
import java.util.Scanner;

public class AGenetico {

	private Cromosoma[] pob; // poblaci�n de individuos
	private Cromosoma[] sigPob; // poblaci�n de individuos
	private int tamPob = 100; // tama�o poblaci�n
	private int numMaxGen = 100; // n�mero m�ximo de generaciones
	private Cromosoma elMejor; // mejor individuo
	private int  pos_mejor; // posici�n del mejor cromosoma
	private double prob_cruce; // probabilidad de cruce
	private double prob_mut; // probabilidad de mutaci�n
	private double tol;
	private int posMejor; // posici�n del mejor cromosoma
	private double probCruce = 0.6; // probabilidad de cruce
	private double probMut = 0.05; // probabilidad de mutaci�n
	private double prec = 0.001;


	public AGenetico() {
		//readParams();
		posMejor = -1;
		pob = new Cromosoma[tamPob];
	}

	public void start() {
		initProblem();
		evaluate();
		int i = 0;
		while(i < numMaxGen) {
			selRuleta();
			reproducir();
			mutaci�n();
			pob = sigPob;
			evaluate();
			i++;
		}

		System.out.println("La soluci�n es: " + elMejor.getFenotipo());
		System.out.println("Lo esperado es: " + CromosomaHolder.calcHolder(-8.05502, -9.66459));

	}

	private void readParams() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce los par�metros base: \n" + "\n - Tam de la Poblaci�n (default 100): ");
		tamPob = Integer.parseInt(sc.next());
		System.out.println(" - Generaciones m�x (default: 100)");
		numMaxGen = Integer.parseInt(sc.next());
		System.out.println(" - % de Cruce (default: 60)");
		probCruce = Double.parseDouble(sc.next()) / 100;
		System.out.println(" - % de Mutaci�n (default: 5)");
		probMut = Double.parseDouble(sc.next()) / 100;
		System.out.println(" - Discretizaci�n del intervalo (default: 0.001)");
		prec = Double.parseDouble(sc.next());
		sc.close();
	}

	public void initProblem() {
		Random rd = new Random();
		for (int i = 0; i < tamPob; i++) {
			pob[i] = new CromosomaHolder(prec);
			pob[i].randomize(rd);
		}
	}

	public void evaluate() {
		double total = 0, acc = 0;
		for (int i = 0; i < tamPob; i++) {
			pob[i].calcFitness();
			if (pob[i].compare(elMejor) == 1) {
				elMejor = pob[i];
				posMejor = i;
			}
			total += pob[i].getFitness();
		}
		for (Cromosoma c : pob) {
			acc += c.getFitness() / total;
			c.setPuntuacionAcc(acc);
			c.setPuntuacion(c.getFitness() / total);
		}
	}

	public void selRuleta() {
		sigPob = new Cromosoma[tamPob];
		Random rng = new Random();
		double sel;
		boolean found = false;
		int j = 0;
		for (int i = 0; i < tamPob; i++) {
			sel = rng.nextDouble();
			while (!found && j < tamPob) {
				if (pob[j].getPuntuacionAcc() < sel)
					found = true;
				j++;
			}
			if(j == tamPob) j--;
			sigPob[i] = pob[j];
			j = 0;
			found = false;
		}
	}

	public void reproducir() {
		Random rng = new Random();
		int[] selec = new int[tamPob];
		int s = 0;
		for (int i = 0; i < tamPob; i++) {
			if (rng.nextDouble() < probCruce) {
				selec[s] = i;
				s++;
			}
		}
		if (s % 2 != 0)
			s--;
		Cromosoma[] hijos = new Cromosoma[2];
		int longitud = sigPob[0].getLongitud(), cruce = rng.nextInt(longitud);
		for (int i = 0; i < s; i += 2) {
			hijos = sigPob[selec[i]].cruce(sigPob[selec[i + 1]], cruce);
			sigPob[selec[i]] = hijos[0];
			sigPob[selec[i + 1]] = hijos[1];
			hijos[0].calcFitness();
			hijos[1].calcFitness();
		}
	}

	public void mutaci�n() {
		boolean mutado;
		int longitud = sigPob[0].getLongitud();
		Random rng = new Random();
		for (int i = 0; i < tamPob; i++) {
			mutado = false;
			for (int j = 0; j < longitud; j++) {
				if (rng.nextDouble() < probMut) {
					mutado = true;
					sigPob[i].mutaBit(j);
				}
			}
			if (mutado) {
				sigPob[i].calcFitness();
			}
		}
	}

	public Cromosoma[] getPob() {
		return pob;
	}

	public void setPob(Cromosoma[] pob) {
		this.pob = pob;
	}

	public int getTamPob() {
		return tamPob;
	}

	public void setTamPob(int tam_pob) {
		this.tamPob = tam_pob;
	}

	public int getNumMaxGen() {
		return numMaxGen;
	}

	public void setNumMaxGen(int num_max_gen) {
		this.numMaxGen = num_max_gen;
	}

	public Cromosoma getElMejor() {
		return elMejor;
	}

	public void setElMejor(Cromosoma elMejor) {
		this.elMejor = elMejor;
	}

	public int getPosMejor() {
		return posMejor;
	}

	public void setPosMejor(int pos_mejor) {
		this.posMejor = pos_mejor;
	}

	public double getProbCruce() {
		return probCruce;
	}

	public void setProbCruce(double prob_cruce) {
		this.probCruce = prob_cruce;
	}

	public double getProbMmut() {
		return probMut;
	}

	public void setProbMut(double prob_mut) {
		this.probMut = prob_mut;
	}

	public double getTol() {
		return prec;
	}

	public void setTol(double tol) {
		this.prec = tol;
	}

}
