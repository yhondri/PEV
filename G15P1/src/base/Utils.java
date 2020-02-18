package base;

public class Utils {
    /**
     * Calcula la longitud de la cadena binaria necesaria para la representación del genotipo.
     *
     * @param min       Valor mínimo el intervalo.
     * @param max       Valor máximo el intervalo.
     * @param tolerance Tolerancia o precisión.
     * @return Devuelve el número de bits de la cadena binaria necesaria para la representación del genotipo.
     */
    public static int getGenotypeLength(double min, double max, double tolerance) {
        double log10 = Math.log(1 + ((max - min) / tolerance));
        double log2 = log10 / Math.log(2);
        return (int) Math.ceil(log2);
    }

    public static double decodeGene(boolean[] chromosome) {
        int decimalValue = binaryToDecimal(chromosome);
        return decimalValue;
    }

    // Function to convert binary to decimal
    public static int binaryToDecimal(boolean[] chromosome) {
        int decimal = 0;
        for(int i=chromosome.length-1, j=0; i>=0; i--, j++) {
            int value = chromosome[i] ? 1 : 0;
            decimal  += value*Math.pow(2, j);
        }

        return decimal;
    }

}
