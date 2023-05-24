import parcs.*;

public class BubbleSort implements AM {
    @Override
    public void run(AMInfo amInfo) {
        int[] array = (int[]) amInfo.parent.readObject();
        int start = amInfo.parent.readInt();
        int end = amInfo.parent.readInt();

        // Perform bubble sort on the received chunk of the array
        boolean sorted = true;
        for (int i = start; i < end; i++) {
            if (array[i] > array[i + 1]) {
                int temp = array[i];
                array[i] = array[i + 1];
                array[i + 1] = temp;
                sorted = false;
            }
        }

        if (!sorted) {
            amInfo.parent.write(array);
            amInfo.parent.write(start);
        } else {
            amInfo.parent.write(new int[0]);
        }
        amInfo.parent.write(null); // Write a placeholder object to unblock the readObject() call in Solver
    }
}
