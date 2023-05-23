import java.io.*;
import java.util.*;
import parcs.*;

public class Solver {
    public static void main(String[] args) {
        task t = new task();
        t.addJarFile("BubbleSort.jar");
        new Main().run(new AMInfo(t, null));
        t.end();
    }

    public void run(AMInfo info) {
    // Read input
    int n = 10; // Number of elements in the array
    int min = 1; // Minimum value for random generation
    int max = 100; // Maximum value for random generation

    int[] array = generateRandomArray(n, min, max);

    // Perform parallel bubble sort
    int workers = info.curtask.getJobList().size();
    int chunkSize = (n - 1) / workers + 1;
    boolean sorted = false;
    while (!sorted) {
        sorted = true;
        var channels = new channel[workers];
        var points = new point[workers];
        for (int i = 0; i < workers; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, n);
            int[] subArray = Arrays.copyOfRange(array, start, end);

            var p = info.createPoint();
            var c = p.createChannel();
            points[i] = p;
            channels[i] = c;
            points[i].execute("BubbleSort");
            c.write(subArray);
        }

        for (var channel : channels) {
            int[] sortedChunk = (int[]) channel.readObject();
            if (sortedChunk == null) {
                System.out.println("Error: Sorting failed. Received null chunk.");
                return;
            }
            if (sortedChunk.length > 0) {
                sorted = false;
                int startIndex = (int) channel.readObject();
                System.arraycopy(sortedChunk, 0, array, startIndex, sortedChunk.length);
            }
        }
    }

    // Write sorted array to output file
    writeArrayToFile(array, "sorted.txt");
}

private int[] generateRandomArray(int n, int min, int max) {
    int[] array = new int[n];
    Random random = new Random();
    for (int i = 0; i < n; i++) {
        array[i] = random.nextInt(max - min + 1) + min;
    }
    return array;
}

private void writeArrayToFile(int[] array, String filePath) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        for (int i = 0; i < array.length; i++) {
            writer.write(String.valueOf(array[i]));
            if (i < array.length - 1) {
                writer.write(" ");
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}


