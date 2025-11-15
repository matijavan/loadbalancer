package LoadBalancer;

import LoadBalancer.Model.Task;
import LoadBalancer.Model.GlobalFunctions;

import java.util.concurrent.BlockingQueue;

import static LoadBalancer.Model.GlobalVariables.nodeReceiverList;

public class NodeWorker implements Runnable{
    private int NodeWorkerNumber;
    private Task taskCurrentlyBeingProcessed;
    @Override
    public void run() {
        System.out.println("Rodio se worker od node " + NodeWorkerNumber);
        while(true){
            NodeReceiver nodeReceiver = findNodeReceiver(NodeWorkerNumber);
            Task task = null;
            try {
                task = takeTaskFromQueue(nodeReceiver.getTaskQueue());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                System.out.println("Worker " + NodeWorkerNumber + ": radim posao " + task.getID() + ", spavamo " + task.getLength() + "ms.");
                Thread.sleep(task.getLength());
                System.out.println("Worker " + NodeWorkerNumber + ": posao " + task.getID() + " je obavljen.");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public NodeWorker(int nodeWorkerNumber) {
        NodeWorkerNumber = nodeWorkerNumber;
    }

    public Task takeTaskFromQueue(BlockingQueue<Task> taskQueue) throws InterruptedException {
        Task task = taskQueue.take();
        return task;
    }

    public NodeReceiver findNodeReceiver(int number) {
        for (NodeReceiver nr : nodeReceiverList) {
            if (nr.getNodeReceiverNumber() == number) {
                return nr;
            }
        }
        return null;
    }
}
