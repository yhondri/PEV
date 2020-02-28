package crossoveralgorithm.floatCrossover;

import base.Pair;
import entities.BinaryChromosome;
import entities.FloatChromosome;

public interface FloatCrossoverAlgorithm {
	Pair<FloatChromosome, FloatChromosome> crossOver(FloatChromosome chromosomeA, FloatChromosome chromosomeB);
}
