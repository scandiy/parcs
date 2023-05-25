import java.io.*;
import java.util.*;

import parcs.*;

public class Main implements AM {
    public static void main(String[] args) {
        System.out.print("class Solver start method main\n");

        task mainTask = new task();

        mainTask.addJarFile("Solver.jar");
        mainTask.addJarFile("Count.jar");

        System.out.print("class Solver method main adder jars\n");

        (new Main()).run(new AMInfo(mainTask, (channel) null));

        System.out.print("class Solver method main finish work\n");

        mainTask.end();
    }

    public void run(AMInfo info) {
        long n, a, b;

        try {
            BufferedReader in = new BufferedReader(new FileReader(info.curtask.findFile("input.txt")));

            n = new Long(in.readLine()).longValue();
            a = new Long(in.readLine()).longValue();
            b = new Long(in.readLine()).longValue();
        } catch (IOException e) {
            System.out.print("Error while reading input\n");
            e.printStackTrace();
            return;
        }

        System.out.print("class Solver method run read data from file\n");

        long[] array = generateArray(n, a, b);

        System.out.println("Original array:");
        printArray(array);

        bubbleSort(array);

        System.out.println("Sorted array:");
        printArray(array);
    }

    static public long[] generateArray(long n, long a, long b) {
        long[] array = new long[(int) n];
        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            array[i] = rand.nextLong() % (b - a + 1) + a;
        }

        return array;
    }

    static public void bubbleSort(long[] array) {
        int n = array.length;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    // Swap elements
                    long temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;
                }
            }

            // If no two elements were swapped, the array is already sorted
            if (!swapped) {
                break;
            }
        }
    }

    static public void printArray(long[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}
