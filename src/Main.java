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

        // Divide the array into equal parts
        int numDaemons = info.cores;
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

        // Perform the final merge sort step on the sorted partitions
        mergeSort(sortedArray);

        System.out.println("Sorted array:");
        for (int num : sortedArray) {
            System.out.print(num + " ");
        }
        System.out.println();

        double totalTime = (double) (System.nanoTime() - startTime) / 1000000000;
        System.out.println("Execution time: " + totalTime + " seconds");

        curtask.end();
    }

    private static void mergeSort(int[] arr) {
        if (arr.length > 1) {
            int mid = arr.length / 2;
            int[] left = new int[mid];
            int[] right = new int[arr.length - mid];

            System.arraycopy(arr, 0, left, 0, mid);
            System.arraycopy(arr, mid, right, 0, arr.length - mid);

            mergeSort(left);
            mergeSort(right);

            merge(arr, left, right);
        }
    }

    private static void merge(int[] arr, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i] < right[j]) {
                arr[k] = left[i];
                i++;
            } else {
                arr[k] = right[j];
                j++;
            }
            k++;
        }

        while (i < left.length) {
            arr[k] = left[i];
            i++;
            k++;
        }

        while (j < right.length) {
            arr[k] = right[j];
            j++;
            k++;
        }
    }
}
