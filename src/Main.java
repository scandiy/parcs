import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

import parcs.*;

public class Main implements AM {

    public static void main(String[] args) throws Exception {
        task curtask = new task();
        curtask.addJarFile("Main.jar");
        curtask.addJarFile("BubbleSort.jar");

        AMInfo info = new AMInfo(curtask, null);

        Scanner sc = new Scanner(new File(curtask.findFile("input.txt")));
        int n = sc.nextInt();
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        long startTime = System.nanoTime();

        List<channel> channels = new ArrayList<>();

        int numDaemons = 2;
        int chunkSize = n / numDaemons;

        for (int i = 0; i < numDaemons; i++) {
            int start = i * chunkSize;
            int end = (i == numDaemons - 1) ? n : (i + 1) * chunkSize;

            int[] partition = new int[end - start];
            System.arraycopy(arr, start, partition, 0, end - start);

            point newPoint = info.createPoint();
            channel newChannel = newPoint.createChannel();

            channels.add(newChannel);

            newChannel.write(partition);
            newChannel.write(end - start);

            newPoint.execute("BubbleSort.jar");
        }

        List<Integer> sortedList = new ArrayList<>();
        for (channel channel : channels) {
            int[] sortedPartition = (int[]) channel.readObject();
            for (int num : sortedPartition) {
                sortedList.add(num);
            }
        }

        int[] sortedArray = new int[n];
        for (int i = 0; i < n; i++) {
            sortedArray[i] = sortedList.get(i);
        }

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
            if (left[i] <= right[j]) {
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

    public void run(AMInfo info) {
        channel channel = info.parent.readChannel();
        int[] partition = (int[]) channel.readObject();
        int length = channel.readInt();

        bubbleSort(partition, length);

        channel.write(partition);
    }

    private void bubbleSort(int[] arr, int length) {
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}
