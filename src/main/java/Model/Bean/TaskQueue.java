package Model.Bean;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TaskQueue {
    private static final BlockingQueue<Task> queue = new LinkedBlockingQueue<>();

    public static void addTask(Task task) {
        queue.add(task);
    }

    public static Task take() throws InterruptedException {
        return queue.take();
    }

    public static int getQueueSize() {
        return queue.size();
    }
}