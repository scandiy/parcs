import java.io.Serializable;
import java.util.List;

public class Worker implements Serializable {
    public List<Integer> mymap(List<Integer> array) {
        // Sorting algorithm (e.g., bubble sort)
        bubbleSort(array);
        return array;
    }

    private void bubbleSort(List<Integer> numbers) {
        int n = numbers.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (numbers.get(j) > numbers.get(j + 1)) {
                    int temp = numbers.get(j);
                    numbers.set(j, numbers.get(j + 1));
                    numbers.set(j + 1, temp);
                }
            }
        }
    }
}
