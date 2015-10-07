package second_stage;

import java.util.Comparator;

/**
 * Comparator for custom SumSetPair type used to sort lists of
 * sums of subsets
 */
public class SumSetComparator implements Comparator<SumSetPair> {
    public int compare(SumSetPair pair1, SumSetPair pair2) {
        return pair1.sum() - pair2.sum();
    }
}
