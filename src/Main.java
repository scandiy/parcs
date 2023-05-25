import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

import parcs.*;

public class Main {
    public static void main(String[] args) throws Exception {
        task curtask = new task();
        curtask.addJarFile("BubbleSort.jar");

        AMInfo info = new AMInfo(curtask, null);

        Scanner sc = new Scanner(new File(curtask.findFile("input")));
        int n = sc.nextInt();
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        long startTime = System.nanoTime();

        List<channel> channels = new ArrayList<>();

        // Define the number of daemons
        int numDaemons = 2; // Adjust the number of daemons as needed

        // Calculate the partition size for each daemon
        int partitionSize = n / numDaemons;

        for (int i = 0; i < numDaemons; i++) {
            int start = i * partitionSize;
            int end = (i == numDaemons - 1) ? n : (i + 1) * partitionSize;

            // Create a new point and channel for each daemon
            point newPoint = info.createPoint();
            channel newChannel = newPoint.createChannel();

            channels.add(newChannel);

            // Pass the partitioned array to each daemon
            int[] partition = new int[end - start];
            System.arraycopy(arr, start, partition, 0, end - start);

            // Send partition to the daemon for sorting
            newChannel.write(partition);

            // Execute the BubbleSort program on the daemon
            newPoint.execute("BubbleSort.jar");
        }

        // Collect sorted partitions from daemons
        List<Integer> sortedList = new ArrayList<>();
        for (channel channel : channels) {
            int[] sortedPartition = (int[]) channel.readObject();
            for (int num : sortedPartition) {
                sortedList.add(num);
            }
        }

        // Merge sorted partitions
        int[] sortedArray = new int[n];
        for (int i = 0; i < n; i++) {
            sortedArray[i] = sortedList.get(i);
        }

        // Perform the final bubble sort step on the sorted partitions
        bubbleSort(sortedArray);

        System.out.println("Sorted array:");
        for (int num : sortedArray) {
            System.out.print(num + " ");
        }
        System.out.println();

        double totalTime = (double) (System.nanoTime() - startTime) / 1000000000;
        System.out.println("Execution time: " + totalTime + " seconds");

        curtask.end();
    }

    private static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}
