package second_stage;

public class Task2PartitionProblem {

    public static void main(String[] args) {
        int numbers[] = {4, 1, 5, 9, 12};
        if (findPartition(numbers, numbers.length))
            System.out.println("Возможно разделить на два подмножества с " +
                    "равными суммами элементов в каждом.");
        else
            System.out.println("Невозможно разделить на два подмножества с " +
                    "равными суммами элементов в каждом.");
    }

    /** Метод, возвращающий true, если существует подмножество массива numbers[]
     * c суммой элементов равной sum
     */
    public static boolean isSubsetSum (int numbers[], int n, int sum) {
        // Базовые случаи
        if (sum == 0)
            return true;
        if (n == 0)
            return false;

        // Если последний элемент больше чем sum, то пропускаем его
        if (numbers[n - 1] > sum)
            return isSubsetSum (numbers, n - 1, sum);

        // Иначе, проверим может ли sum быть получена любым из следующих
        // способов:
        // 1) включением последнего элемента
        // 2) исключением последнего элемента
        return isSubsetSum (numbers, n - 1, sum) || isSubsetSum (numbers, n-1,
                sum-numbers[n-1]);
    }

    /** Возвращает true, если numbers[] может быть разделен на два
     * подмножества с равными суммами элементов, иначе false
     */
    public static boolean findPartition (int numbers[], int n) {
        // Найдем сумму элементов в массиве
        int sum = 0;
        for (int i = 0; i < n; i++)
            sum += numbers[i];

        // Если sum - нечетная, то не может существовать двух подмножеств
        // c равными суммами
        if ((sum % 2) != 0)
            return false;

        // Выяснить существует ли подмножество с суммой, равной половине всей
        // суммы sum
        return isSubsetSum (numbers, n, sum / 2);
    }
}
