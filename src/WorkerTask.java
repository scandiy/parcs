import parcs.AM;
import parcs.AMInfo;
import parcs.channel;

import java.util.List;

public class WorkerTask implements AM {
    public void run(AMInfo info) {
        List<Integer> chunk = (List<Integer>) info.parent_read().readObject();
        Worker worker = (Worker) info.parent_read().readObject();
        List<Integer> sortedChunk = worker.mymap(chunk);

        info.parent_write().write(sortedChunk);
    }
}
