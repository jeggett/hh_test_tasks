package second_stage;

import java.util.Set;

/**
 * Custom datatype used to recover initial
 * subset after from the sum of subset
 */
public class SumSetPair {
    private int sum;
    private final Set<MyPair> set;

    public SumSetPair(Set<MyPair> aSet) {
        set = aSet;
    }

    public int sum()   {
        sum = 0;
        for (MyPair pair : set) {
            sum = sum + pair.value();
        }
        return sum;
    }
    public Set<MyPair> set() { return set; }
}