package LoadBalancer;

import LoadBalancer.Model.Task;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class NodeReceiver implements Runnable{
    @Override
    public void run() {
        System.out.println("Rodio se reciever od node 1");
        while(true){
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int NodeReceiverNumber;
    private BlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>();

    public int getNodeReceiverNumber() {
        return NodeReceiverNumber;
    }

    public void setNodeReceiverNumber(int nodeReceiverNumber) {
        NodeReceiverNumber = nodeReceiverNumber;
    }

    public BlockingQueue<Task> getTaskQueue() {
        return taskQueue;
    }

    public void setTaskQueue(BlockingQueue<Task> taskQueue) {
        this.taskQueue = taskQueue;
    }

    public NodeReceiver(int nodeReceiverNumber) {
        NodeReceiverNumber = nodeReceiverNumber;
        BlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>();
    }



}
