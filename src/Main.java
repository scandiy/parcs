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

        Scanner sc = new Scanner(new File(curtask.findFile("input.txt")));
        int n = sc.nextInt();
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        int numPoints = 2; // Number of points (daemons) to use
        int batchSize = n / numPoints; // Number of elements to assign to each point

        List<point> points = new ArrayList<>();
        List<channel> channels = new ArrayList<>();

        // Create points and channels
        for (int i = 0; i < numPoints; i++) {
            point p = info.createPoint();
            channel c = p.createChannel();
            points.add(p);
            channels.add(c);
        }

        // Assign numbers to points
        int startIndex = 0;
        int endIndex = batchSize - 1;

        for (int i = 0; i < numPoints; i++) {
            point p = points.get(i);
            channel c = channels.get(i);

            // Send numbers to the point
            for (int j = startIndex; j <= endIndex; j++) {
                c.write(arr[j]);
            }

            // Notify the point that there are no more numbers
            c.write(-1);

            // Update the start and end indices for the next point
            startIndex = endIndex + 1;
            endIndex = startIndex + batchSize - 1;
        }

        // Wait for the points to finish
        for (int i = 0; i < numPoints; i++) {
            points.get(i).execute("BubbleSort");
        }

        // Collect the sorted numbers from each point
        List<Integer> sortedNumbers = new ArrayList<>();

        for (int i = 0; i < numPoints; i++) {
            point p = points.get(i);
            channel c = channels.get(i);

            while (true) {
                int num = c.readInt();
                if (num == -1) {
                    break; // Reached the end of numbers from this point
                }
                sortedNumbers.add(num);
            }

            p.delete();
            c.close();
        }

        // Merge and print the sorted numbers
        int[] mergedSortedNumbers = new int[sortedNumbers.size()];
        for (int i = 0; i < sortedNumbers.size(); i++) {
            mergedSortedNumbers[i] = sortedNumbers.get(i);
        }

        // Perform final sorting on the merged numbers
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (mergedSortedNumbers[j] > mergedSortedNumbers[j + 1]) {
                    int temp = mergedSortedNumbers[j];
                    mergedSortedNumbers[j] = mergedSortedNumbers[j + 1];
                    mergedSortedNumbers[j + 1] = temp;
                }
            }
        }

        System.out.println("Sorted array:");
        for (int num : mergedSortedNumbers) {
            System.out.print(num + " ");
        }
        System.out.println();

        curtask.end();
    }
}
