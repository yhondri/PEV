package helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataFileReader {
    public static List<List<Boolean>> readFile(String fileName) throws FileNotFoundException {
        InputStream fileInputStream = new ResourceHelper().getFileInputStream(fileName);
        // Read input file
        Scanner input = new Scanner(fileInputStream);
        // This should be here to get size of array before getting each array
        List<List<Boolean>> data = new ArrayList<>();
        int numberOfRows = 0;
        int numberOfDataByRows = 0;
        if (input.hasNextInt()) {
            numberOfRows = input.nextInt();
        }
        if (input.hasNextInt()) {
            numberOfDataByRows = input.nextInt();
        }
        for (int i = 0; i < numberOfRows; i++) {
            data.add(new ArrayList<>());
            for (int j = 0; j < numberOfDataByRows; j++) {
                try {
                    Boolean value = (input.nextInt() == 1) ? true : false;
                    data.get(i).add(value);
                } catch (java.util.NoSuchElementException e) {
                    // e.printStackTrace();
                }
            }
        }
        return data;
    }
}
