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

        List<point> points = new ArrayList<>();
        List<channel> channels = new ArrayList<>();

        for (int i = 0; i < info.nodes().size(); i++) {
            point p = info.createPoint();
            points.add(p);
            channels.add(p.createChannel());
        }

        for (int i = 0; i < n; i++) {
            int value = arr[i];
            channel c = channels.get(i % info.nodes().size());
            c.write(value);
        }

        for (channel c : channels) {
            c.close();
        }

        List<Integer> sortedNumbers = new ArrayList<>();

        for (int i = 0; i < info.nodes().size(); i++) {
            channel c = points.get(i).createChannel();
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
