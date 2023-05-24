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

        point p = info.createPoint();
        channel c = p.createChannel();
        p.execute("BubbleSort");
        c.write(arr);
        channel resultChannel = p.createChannel();
        p.getMonitor().addChannel(resultChannel);

        p.execute("BubbleSort");
        c.write(-1); // Signal the end of input numbers

        List<Integer> sortedNumbers = new ArrayList<>();

        while (true) {
            int sortedNum = resultChannel.readInt();
            if (sortedNum == -1) {
                break; // Exit the loop when all numbers are received
            }
            sortedNumbers.add(sortedNum);
        }

        System.out.println("Sorted array:");
        for (int num : sortedNumbers) {
            System.out.print(num + " ");
        }
        System.out.println();

        double totalTime = (double) (System.nanoTime() - startTime) / 1000000000;
        System.out.println("Execution time: " + totalTime + " seconds");

        curtask.end();
    }
}
