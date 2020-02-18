package crossoveralgorithm;
import base.*;

public interface CrossoverAlgorithm {
    Pair<boolean[], boolean[]>crossOver(boolean[] chromosomeA, boolean[] chromosomeB, double crossOverProbability);


}
