import java.util.List;
import java.util.ArrayList;

import parcs.*;

public class BubbleSort implements AM {
    public void run(AMInfo info) {
        List<Integer> numbers = new ArrayList<>();

        while (info.parent.available() > 0) {
            int num = info.parent.readInt();
            numbers.add(num);
        }

        System.out.println("Received " + numbers.size() + " numbers. Sorting...");

        boolean swapped;
        int n = numbers.size();
        do {
            swapped = false;
            for (int i = 0; i < n - 1; i++) {
                if (numbers.get(i) > numbers.get(i + 1)) {
                    int temp = numbers.get(i);
                    numbers.set(i, numbers.get(i + 1));
                    numbers.set(i + 1, temp);
                    swapped = true;
                }
            }
            n--;
        } while (swapped);

        System.out.println("Sorting completed. Sending sorted numbers.");

        for (int num : numbers) {
            info.parent.write(num);
        }
    }
}
