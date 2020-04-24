package helper;

import java.util.Random;

public class Utils {
    public static final Random random = new Random();
    public static int getRandom(int high, int low) {
        return random.nextInt(high-low) +  low;
    }
}
