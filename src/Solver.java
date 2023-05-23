import parcs.AM;
import parcs.AMInfo;
import parcs.channel;
import parcs.point;

import java.io.*;
import java.util.*;

public class Solver implements AM {

    public void run(AMInfo info) {
        List<Worker> workers = new ArrayList<>();
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        Worker worker3 = new Worker();
        Worker worker4 = new Worker();
        workers.add(worker1);
        workers.add(worker2);
        workers.add(worker3);
        workers.add(worker4);

        System.out.println("Inited");
        System.out.println("Workers: " + workers.size());
        int n = readInput();

        // Generate random numbers
        List<Integer> numbers = generateRandomNumbers(n);

        // Split the numbers into chunks for each worker
        List<List<Integer>> chunks = chunk(numbers, workers.size());

        // Map: Sort the chunks
        List<List<Integer>> mapped = new ArrayList<>();
        long startTime = System.currentTimeMillis(); // Start time measurement

        for (int i = 0; i < workers.size(); i++) {
            List<Integer> chunk = chunks.get(i);
            point p = info.createPoint();
            channel c = p.createChannel();
            p.execute("WorkerTask");
            c.write(chunk);
            c.write(workers.get(i));
            p.join();
            List<Integer> sortedChunk = c.readObject();
            mapped.add(sortedChunk);
        }

        long endTime = System.currentTimeMillis(); // End time measurement
        long totalTime = endTime - startTime; // Calculate elapsed time

        // Reduce: Merge the sorted chunks
        List<Integer> reduced = myreduce(mapped);

        // Output: Write the sorted numbers to file
        writeOutput(reduced);

        System.out.println("Job Finished");
        System.out.println("Elapsed Time: " + totalTime + " milliseconds");
        info.parent().signal();
    }

    private List<Integer> generateRandomNumbers(int n) {
        List<Integer> numbers = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            numbers.add(random.nextInt(1000000) + 1);
        }

        return numbers;
    }

    private List<List<Integer>> chunk(List<Integer> numbers, int n) {
        List<List<Integer>> chunks = new ArrayList<>();
        int chunkSize = (int) Math.ceil((double) numbers.size() / n);

        for (int i = 0; i < numbers.size(); i += chunkSize) {
            int endIndex = Math.min(i + chunkSize, numbers.size());
            chunks.add(numbers.subList(i, endIndex));
        }

        return chunks;
    }

    private int readInput() {
        int n = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            n = Integer.parseInt(reader.readLine());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return n;
    }

    private void writeOutput(List<Integer> numbers) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            for (int number : numbers) {
                writer.write(String.valueOf(number));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Integer> myreduce(List<List<Integer>> mapped) {
        List<Integer> result = new ArrayList<>();
        for (List<Integer> list : mapped) {
            result.addAll(list);
        }
        result.sort(null);
        return result;
    }

    public static void main(String[] args) {
        Solver solver = new Solver();
        solver.run(new AMInfo(null));
    }
}

