import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import parcs.*;

public class Solver implements AM {
    public static void main(String[] args) {
        task t = new task();
        t.addJarFile("BubbleSort.jar");
        (new Solver()).run(new AMInfo(t, null));
        t.end();
    }

    @Override
    public void run(AMInfo info) {
        // Read input
        int n = 10; // Number of elements in the array
        int min = 1; // Minimum value for random generation
        int max = 100; // Maximum value for random generation

        int[] array = generateRandomArray(n, min, max);

        // Perform parallel bubble sort
        int workers = info.getTask().getCt();
        int chunkSize = (n - 1) / workers + 1;
        boolean sorted = false;

        point[] points = new point[workers];
        channel[] channels = new channel[workers];

        for (int i = 0; i < workers; i++) {
            point p = info.createPoint();
            channel c = p.createChannel();
            points[i] = p;
            channels[i] = c;
            p.execute("BubbleSort");
            c.write(array);
            c.write(i * chunkSize);
            c.write(Math.min((i + 1) * chunkSize - 1, n - 1));
        }

        for (channel channel : channels) {
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

        if (!sorted) {
            sorted = true;
            for (int i = 0; i < n - 1; i++) {
                if (array[i] > array[i + 1]) {
                    sorted = false;
                    break;
                }
            }
        }

        // Write sorted array to output file
        writeArrayToFile(array, "sorted.txt");

        System.out.println("Sorting " + (sorted ? "succeeded" : "failed"));
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
