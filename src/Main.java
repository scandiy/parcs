import java.io.*;
import java.math.*;
import java.util.*;

import parcs.*;

public class Solver implements AM {
    public static void main(String[] args) {
        System.out.print("class Solver start method main\n");

        task mainTask = new task();

        mainTask.addJarFile("Solver.jar");
        mainTask.addJarFile("BubbleSort.jar");

        System.out.print("class Solver method main adder jars\n");

        (new Solver()).run(new AMInfo(mainTask, (channel) null));

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

        long tStart = System.nanoTime();

        long[] sortedArray = parallelBubbleSort(info, array);

        long tEnd = System.nanoTime();

        System.out.println("Sorted array:");
        printArray(sortedArray);

        System.out.println("Time: " + ((tEnd - tStart) / 1000000) + "ms");
    }

    static public long[] generateArray(long n, long a, long b) {
        long[] array = new long[(int) n];
        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            array[i] = rand.nextLong() % (b - a + 1) + a;
        }

        return array;
    }

    static public long[] parallelBubbleSort(AMInfo info, long[] array) {
        int numWorkers = info.coresNum();

        // Split the array into equal-sized chunks
        int chunkSize = array.length / numWorkers;

        List<point> points = new ArrayList<>();
        List<channel> channels = new ArrayList<>();

        for (int i = 0; i < numWorkers; i++) {
            int startIndex = i * chunkSize;
            int endIndex = (i == numWorkers - 1) ? array.length : (i + 1) * chunkSize;

            System.out.println("Worker " + i + " range: " + startIndex + " - " + (endIndex - 1));

            point newPoint = info.createPoint();
            channel newChannel = newPoint.createChannel();

            channels.add(newChannel);
            points.add(newPoint);

            newPoint.execute("BubbleSort");
            newChannel.write(array);
            newChannel.write(startIndex);
            newChannel.write(endIndex);
        }

        long[] sortedArray = new long[array.length];
        for (int i = 0; i < numWorkers; i++) {
            long[] workerResult = channels.get(i).readObject(long[].class);
            System.arraycopy(workerResult, 0, sortedArray, i * chunkSize, workerResult.length);
        }

        // Merge the sorted chunks
        mergeSortedChunks(sortedArray, chunkSize);

        return sortedArray;
    }

    static public void mergeSortedChunks(long[] array, int chunkSize) {
        int n = array.length;

        for (int i = chunkSize; i < n; i *= 2) {
            for (int j = 0; j < n - i; j += i * 2) {
                int mid = j + i - 1;
                int rightEnd = Math.min(j + i * 2 - 1, n - 1);

                merge(array, j, mid, rightEnd);
            }
        }
    }

    static public void merge(long[] array, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        long[] leftArray = new long[n1];
        long[] rightArray = new long[n2];

        System.arraycopy(array, left, leftArray, 0, n1);
        System.arraycopy(array, mid + 1, rightArray, 0, n2);

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }

    static public void printArray(long[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}
