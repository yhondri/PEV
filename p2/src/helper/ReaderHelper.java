package helper;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class ReaderHelper {
    private int[][] flujoMatrix;
    private int[][] distanciaMatrix;
    private int chromosomeSize;

    public void readFile(InputStream fileName) throws FileNotFoundException {

//        try {
            // Read input file

            Scanner input = new Scanner(fileName);

            chromosomeSize = 0;
            if (input.hasNextInt()) {
                chromosomeSize = input.nextInt();
            }

            boolean didReadDistanciaMatrix = false;

            while (input.hasNextInt()) {
                // This should be here to get size of array before getting each array
                int[][] a = new int[chromosomeSize][chromosomeSize];

                for (int i = 0; i < chromosomeSize; i++) {
                    for (int j = 0; j < chromosomeSize; j++) {

                        try {
                            a[i][j] = input.nextInt();

                        } catch (java.util.NoSuchElementException e) {
                            // e.printStackTrace();
                        }
                    }
                }

                if (!didReadDistanciaMatrix) {
                    distanciaMatrix = a;
                    didReadDistanciaMatrix = true;
                } else {
                    flujoMatrix = a;
                }

                //print the input matrix
//                System.out.println("The input sorted matrix is : ");
//                for (int i = 0; i < chromosomeSize; i++) {
//                    for (int j = 0; j < chromosomeSize; j++) {
//                        System.out.printf("%5d ", a[i][j]);
//                    }
//                    System.out.println();
//
//                }
            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public int[][] getFlujoMatrix() {
        return flujoMatrix;
    }

    public int[][] getDistanciaMatrix() {
        return distanciaMatrix;
    }

    public int getChromosomeSize() {
        return chromosomeSize;
    }
}
