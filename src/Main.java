import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

import parcs.*;

public class Main implements AM {
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

        for (int i = 0; i < n; i++) {
            point p = info.createPoint();
            channel c = p.createChannel();
            p.execute("BubbleSort");
            c.write(arr[i]);
            channels.add(c);
        }

        List<Integer> sortedNumbers = new ArrayList<>();

        for (channel c : channels) {
            sortedNumbers.add(c.readInt());
            c.close();
        }

        System.out.println("Received " + sortedNumbers.size() + " numbers. Sorting completed.");
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
