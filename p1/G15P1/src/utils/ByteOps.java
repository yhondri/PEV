package utils;

public class ByteOps {
	
	public static double bitstreamToReal(boolean bits[]) {
		double ret = 0;
		for(int i = 0; i < bits.length; i++) {
			ret += ret*2 + (bits[i]? 1:0);
		}
		return ret;
	}
	
	// Function to convert binary to decimal
    public static int binaryToDecimal(boolean[] chromosome, int start, int end) {
        int decimal = 0;
        for(int i = end, j = start; i >= start; i--, j++) {
            int value = chromosome[i] ? 1 : 0;
            decimal  += value*Math.pow(2, j);
        }
        
        return decimal;
    }
	
	public static double parseBitStream(boolean[] genes, double min, double max, int longitud) {
		double valReal = ByteOps.binaryToDecimal(genes, 0, longitud -1);
		double val = min + valReal/(Math.pow(2, longitud)- 1)*(max - min);
		return val;
	}
	
	public static boolean[][] splitBitStream(boolean[] bits){
		boolean a[] = new boolean[bits.length/2];
		boolean b[] = new boolean[bits.length/2];
		for (int i = 0; i < bits.length; i++)
		{
			if (i < a.length)
				a[i] = bits[i];
			else
				b[i - a.length] = bits[i];
		}
		return new boolean[][]{a,b};
	}
}
