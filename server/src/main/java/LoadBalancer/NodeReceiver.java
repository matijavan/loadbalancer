package LoadBalancer;

import LoadBalancer.Model.Task;

import java.util.LinkedList;
import java.util.Queue;

public class NodeReceiver implements Runnable{
    @Override
    public void run() {

    }

    private int NodeReceiverNumber;
    private Queue<Task> taskQueue;

    public int getNodeReceiverNumber() {
        return NodeReceiverNumber;
    }

    public void setNodeReceiverNumber(int nodeReceiverNumber) {
        NodeReceiverNumber = nodeReceiverNumber;
    }

    public Queue<Task> getTaskQueue() {
        return taskQueue;
    }

    public void setTaskQueue(Queue<Task> taskQueue) {
        this.taskQueue = taskQueue;
    }

    public NodeReceiver(int nodeReceiverNumber) {
        NodeReceiverNumber = nodeReceiverNumber;
        Queue<Task> taskQueue = new LinkedList<>();
    }

    public void sendTaskToQueue(Task task, Queue<Task> taskQueue){
        taskQueue.offer(task);
    }

    public static NodeReceiver
}
