import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Solver {
    private List<Worker> workers;
    private String inputFileName;
    private String outputFileName;

    public Solver(List<Worker> workers, String inputFileName, String outputFileName) {
        this.workers = workers;
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
        System.out.println("Inited");
    }

    public void solve() {
        System.out.println("Job Started");
        System.out.println("Workers: " + workers.size());
        int n = readInput();

        // Generate random numbers
        List<Integer> numbers = generateRandomNumbers(n);

        // Split the numbers into chunks for each worker
        List<List<Integer>> chunks = chunk(numbers, workers.size());

        // Map: Sort the chunks
        List<List<Integer>> mapped = new ArrayList<>();
        for (int i = 0; i < workers.size(); i++) {
            mapped.add(workers.get(i).mymap(chunks.get(i)));
        }

        // Reduce: Merge the sorted chunks
        List<Integer> reduced = myreduce(mapped);

        // Output: Write the sorted numbers to file
        writeOutput(reduced);

        System.out.println("Job Finished");
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
            BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
            n = Integer.parseInt(reader.readLine());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return n;
    }

    private void writeOutput(List<Integer> numbers) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));
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
        // Create a list of workers
        List<Worker> workers = new ArrayList<>();
        
        // Add workers to the list (initialize them as needed)
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        workers.add(worker1);
        workers.add(worker2);

        // Create a Solver instance and start the sorting process
        Solver solver = new Solver(workers, "input.txt", "output.txt");
        solver.solve();
    }
}

class Worker {
    public List<Integer> mymap(List<Integer> array) {
        // Sorting algorithm (e.g., bubble sort)
        bubbleSort(array);
        return array;
    }

    private void bubbleSort(List<Integer> numbers) {
        int n = numbers.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (numbers.get(j) > numbers.get(j + 1)) {
                    int temp = numbers.get(j);
                    numbers.set(j, numbers.get(j + 1));
                    numbers.set(j + 1, temp);
                }
            }
        }
    }
}
