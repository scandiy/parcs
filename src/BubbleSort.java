import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import parcs.*;

public class BubbleSort implements AM {
    public void run(AMInfo info) {
        int[] numbers = (int[]) info.parent.readObject();

        System.out.println("Received array: " + Arrays.toString(numbers));

        // Sort the array using bubble sort
        for (int i = 0; i < numbers.length - 1; i++) {
            for (int j = 0; j < numbers.length - i - 1; j++) {
                if (numbers[j] > numbers[j + 1]) {
                    int temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                }
            }
        }

        System.out.println("Sorted array: " + Arrays.toString(numbers));

        // Send the sorted array back to the parent point
        info.parent.write(numbers);
    }
}
