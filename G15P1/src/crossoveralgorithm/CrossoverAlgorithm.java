package crossoveralgorithm;
import base.*;
import entities.Chromosome;

public interface CrossoverAlgorithm {
    Pair<Chromosome, Chromosome>crossOver(Chromosome chromosomeA, Chromosome chromosomeB, double crossOverProbability);
}
