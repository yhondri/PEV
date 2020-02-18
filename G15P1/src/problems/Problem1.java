package problems;

import base.Utils;

public class Problem1 implements GenericProblem {

    double minX1 = -3, minX2 = 4.1, maxX1 = 12.1, maxX2 = 5.8, tolerance = 0.001;

        //    return 21.5 + gene1 * Math.sin(4 * Math.PI * gene1) + gene2 * Math.sin(20 * Math.PI * gene2);

    public double evaluate(boolean[] genesA, boolean[] genesB) {
        double phenotypeA = getPhenotype(genesA, minX1, maxX1, genesA.length);
        double phenotypeB = getPhenotype(genesB, minX2, maxX2, genesB.length);
        return 21.5 + phenotypeA * Math.sin(4 * Math.PI * phenotypeA) + phenotypeB * Math.sin(20 * Math.PI * phenotypeB);
    }

    double getPhenotype(boolean[] chromosome, double xmin, double xmax, double chromosomeSize) {
        int decimalValue = Utils.binaryToDecimal(chromosome);
        return xmin + (decimalValue * ((xmax - xmin)/(Math.pow(2, chromosomeSize) - 1)));
    }
}
