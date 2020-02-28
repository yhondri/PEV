package crossoveralgorithm.binaryCrossover;

import base.*;
import entities.BinaryChromosome;

public interface BinaryCrossoverAlgorithm {
	Pair<BinaryChromosome, BinaryChromosome> crossOver(BinaryChromosome chromosomeA, BinaryChromosome chromosomeB);
}
