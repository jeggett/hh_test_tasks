package second_stage;

import java.util.*;

/**
 * Solution for Task 2
 */
public class Task2 {

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter number of elements: ");
        int N = input.nextInt();
        int numbers[] = new int[N];
        System.out.print("Enter elements by a space or newline: ");
        for (int i = 0; i < N; i++) {
            numbers[i] = input.nextInt();
        }

        // test subset sum problem for 100
        int sum = 0;
        for (int i = 0; i < numbers.length; i++) {
            sum += numbers[i];
        }
        int numbersExtended[] = new int[numbers.length + 1];
        for (int i=0; i < numbers.length; i++)
            numbersExtended[i] = numbers[i];
        numbersExtended[numbers.length] = sum - 2 * 100;
        boolean is100 = findPartition(numbersExtended);

        if (findPartition(numbers)) {
            System.out.println(getPartition(numbers));
            if (is100)
                System.out.println("yes");
            else
                System.out.println("no");
        }
        else
            System.out.println("It's not possible to partition initial set" +
                    " on two subsets with equal sums of elements");

    }

    /** Method that returns true if there is a subset of arr[]
     *  with sun equal to given sum
     */
    public static boolean isSubsetSum (int numbers[], int n, int sum) {
        // Base cases
        if (sum == 0)
            return true;
        if (n == 0)
            return false;

        // If last element is greater than sum, then ignore it
        if (numbers[n - 1] > sum)
            return isSubsetSum (numbers, n - 1, sum);

        // else, check if sum can be obtained by any of the following
        //(a) including the last element
        //(b) excluding the last element
        return isSubsetSum (numbers, n - 1, sum) || isSubsetSum (numbers, n-1,
                sum-numbers[n-1]);
    }

    /** Returns true if set can be partitioned in two subsets of
     * equal sum, otherwise false
     *
     * @param numbers initial set of integers placed in array
     * @return true or false
     */
    public static boolean findPartition (int numbers[]) {
        // Calculate sum of the elements in array
        int sum = 0;
        for (int i = 0; i < numbers.length; i++)
            sum += numbers[i];

        // If sum is odd, there cannot be two subsets with equal sum
        if ((sum % 2) != 0)
            return false;

        // Find if there is subset with sum equal to half of total sum
        return isSubsetSum (numbers, numbers.length, sum / 2);
    }

    /**
     * Takes array and outputs an partition of it on two subsets
     * with equal sums
     * @param arrayInt initial array converted two set within method
     * @return formatted string with output of partition
     */
    public static String getPartition (int arrayInt[]) {
        // Partition initial array to two subsets of pairs (id,value)
        // to prevent equal integers from disappearing while adding to sets

        int total = 0;
        for (int i = 0; i < arrayInt.length; i++)
            total += arrayInt[i];

        Set<MyPair> subSet1 = new HashSet<>();
        for (int i = 0; i < arrayInt.length / 2 + 1; i++) {
            // Generate unique id
            String uniqueID = UUID.randomUUID().toString();
            MyPair pair = new MyPair(uniqueID, arrayInt[i]);
            subSet1.add(pair);
        }

        Set<MyPair> subSet2 = new HashSet<>();
        for (int i = arrayInt.length / 2 + 1; i < arrayInt.length; i++) {
            String uniqueID = UUID.randomUUID().toString();
            MyPair pair = new MyPair(uniqueID, arrayInt[i]);
            subSet2.add(pair);
        }

        // Full set. Will be used for output later
        Set<MyPair> fullSet = new HashSet<>(subSet1);
        fullSet.addAll(subSet2);

        // sets1 and sets2 are powersets of subset1 and subset2 respectively
        Set<Set<MyPair>> sets1 = new HashSet<Set<MyPair>>();
        Set<Set<MyPair>> sets2 = new HashSet<Set<MyPair>>();
        sets1 = powerSet(subSet1);
        sets2 = powerSet(subSet2);

        // Convert sets1 and sets2 to list of pairs (set, sumOfAllElements)
        List<SumSetPair> listOfSets1 = new ArrayList<>();
        for (Set<MyPair> set : sets1) {
            listOfSets1.add(new SumSetPair(set));
        }
        List<SumSetPair> listOfSets2 = new ArrayList<>();
        for (Set<MyPair> set : sets2) {
            listOfSets2.add(new SumSetPair(set));
        }

        // Sort both lists
        Collections.sort(listOfSets1, new SumSetComparator());
        Collections.sort(listOfSets2, new SumSetComparator());

        // Finally find equal appropriate subsets by comparing their sums
        String output = "";
        SumSetPair elementSumSetPair1, elementSumSetPair2;
        for (int i = 0, j = listOfSets2.size() - 1; (i < listOfSets1.size() - 1) || (j > 0 ); ) {
            elementSumSetPair1 =listOfSets1.get(i);
            elementSumSetPair2 =listOfSets2.get(j);

            int sumOfSums = elementSumSetPair1.sum() + elementSumSetPair2.sum();
            if (sumOfSums < (total / 2) && (i < listOfSets1.size() - 1)) {
                i++;
            }
            else if (sumOfSums > (total / 2) && (j > 0)) {
                j--;
            }
            else {
                output = "";
                for (MyPair outputSet : elementSumSetPair1.set()) {
                    output += outputSet.value() + " ";
                    fullSet.remove(outputSet);
                }
                for (MyPair outputSet : elementSumSetPair2.set()) {
                    output += outputSet.value() + " ";
                    fullSet.remove(outputSet);
                }
                output += "- ";
                for (MyPair outputSet : fullSet) {
                    output += outputSet.value() + " ";
                }
                break;
            }
        }
        return output;

    }

    /**
     * Method takes set and returns its powerset e.g. set off all
     * possible subsets using recursive approach
     * @param originalSet initial set to build a powerset upon it
     * @return set consisting of all possible subsets of originalSet
     */
    public static Set<Set<MyPair>> powerSet(Set<MyPair> originalSet) {
        Set<Set<MyPair>> sets = new HashSet<Set<MyPair>>();
        if (originalSet.isEmpty()) {
            sets.add(new HashSet<MyPair>());
            return sets;
        }
        List<MyPair> list = new ArrayList<MyPair>(originalSet);
        MyPair head = list.get(0);
        Set<MyPair> rest = new HashSet<MyPair>(list.subList(1, list.size()));
        for (Set<MyPair> set : powerSet(rest)) {
            Set<MyPair> newSet = new HashSet<MyPair>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }
        return sets;
    }

}
