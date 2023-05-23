import parcs.*;

public class BubbleSort implements AM {
    @Override
    public void run(AMInfo amInfo) {
        int[] array = (int[]) amInfo.parent.readObject();

        // Perform bubble sort on the received chunk of the array
        boolean sorted = true;
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            if (array[i] > array[i + 1]) {
                int temp = array[i];
                array[i] = array[i + 1];
                array[i + 1] = temp;
                sorted = false;
            }
        }

        if (!sorted) {
            int startIndex = (int) (amInfo.taskID().pointID() - 1) * (n - 1) / amInfo.getJob().getN();
            amInfo.parent.write(array);
            amInfo.parent.write(startIndex);
        } else {
            amInfo.parent.write(new int[0]);
        }
    }
}
