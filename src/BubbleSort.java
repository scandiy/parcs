import java.util.ArrayList;
import java.util.List;

import parcs.*;

public class BubbleSort implements AM {
    public void run(AMInfo info) {
        int number = info.parent.readInt();
        List<Integer> numbers = new ArrayList<>();
        numbers.add(number);

        System.out.println("Received number: " + number);

        // Sort the number using bubble sort
        for (int i = 0; i < numbers.size() - 1; i++) {
            for (int j = 0; j < numbers.size() - i - 1; j++) {
                if (numbers.get(j) > numbers.get(j + 1)) {
                    int temp = numbers.get(j);
                    numbers.set(j, numbers.get(j + 1));
                    numbers.set(j + 1, temp);
                }
            }
        }

        int sortedNumber = numbers.get(0);
        System.out.println("Sorted number: " + sortedNumber);

        // Send the sorted number back to the parent point
        info.parent.write(sortedNumber);
    }
}
