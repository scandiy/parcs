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

        List<channel> channels = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            point p = info.createPoint();
            channel c = p.createChannel();
            p.execute("BubbleSort");
            c.write(arr[i]);
            channels.add(c);
        }

        int[] sortedArr = new int[n];
        for (int i = 0; i < n; i++) {
            channel c = channels.get(i);
            sortedArr[i] = c.readInt();
            c.close();
        }

        System.out.println("Sorted array:");
        for (int num : sortedArr) {
            System.out.print(num + " ");
        }
        System.out.println();
        
        curtask.end();
    }
}    
