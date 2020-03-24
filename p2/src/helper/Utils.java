package helper;

import java.util.HashSet;
import java.util.Iterator;
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

    public static Pair<Integer, Integer> getMaxMinPosition(int maxElement) {
        Set<Integer> positions = Utils.pickUniqueRandomList(2, maxElement);
        Iterator<Integer> integerIterator =  positions.iterator();
        int temp1, temp2;
        temp1 = integerIterator.next();
        temp2 = integerIterator.next();
        int initialPoint =  Math.min(temp1, temp2);
        int endPoint = Math.max(temp1, temp2);
        return new Pair<>(initialPoint, endPoint);
    }
}
