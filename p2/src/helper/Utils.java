package helper;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Utils {
    public static final Random random = new Random();

    public static int getRandom(int high, int low) {
        return random.nextInt(high-low) +  low;
    }

    public static Set<Integer> pickUniqueRandomList(int n, int k) {
        final Set<Integer> picked = new HashSet<>();
        while (picked.size() < n) {
            picked.add(random.nextInt(k + 1));
        }
        return picked;
    }
}
