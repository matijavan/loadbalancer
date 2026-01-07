package LoadBalancer.Service;

import LoadBalancer.Model.NodeReceiver;
import LoadBalancer.Model.NodeWorker;
import LoadBalancer.Model.Task;
import LoadBalancer.Model.TaskGenerator;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import static LoadBalancer.Model.GlobalVariables.*;

@Service
public class NodeWorkerService implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(NodeWorkerService.class);
    private final List<Thread> workerThreads = new LinkedList<>();

    @Override
    public void run() {

    }

    public void createNodeWorker(int nodeCount) {
        NodeWorker nodeworker = new NodeWorker(nodeCount);
        nodeWorkerList.add(nodeworker);
        setNodeCount(nodeCount + 1); // nodeworker se zadnji kreira pa onda inkrementiram nodeCount
    }

    public void deleteNodeWorker(int nodeNumber) {
        int indexToRemove = nodeNumber - 1;
        nodeWorkerList.remove(indexToRemove);

        for (int i = indexToRemove; i < nodeWorkerList.size(); i++) {
            NodeWorker nodeWorker = nodeWorkerList.get(i);
            nodeWorker.setNodeWorkerNumber(nodeWorker.getNodeWorkerNumber() - 1);
        }

        setNodeCount(nodeCount - 1);
    }

    public void startAllWorkers() {
        for (NodeWorker nw : nodeWorkerList) {
            Thread t = new Thread(() -> runWorker(nw));
            workerThreads.add(t);
            t.start();
        }
    }

    public void stopAllWorkers() {
        for(Thread t :  workerThreads){
            t.interrupt();
        }
        workerThreads.clear();
    }

    public void runWorker(NodeWorker nodeWorker) {
        System.out.println("Spawned Worker for Node " + nodeWorker.getNodeWorkerNumber());
        NodeReceiver nodeReceiver = findNodeReceiver(nodeWorker.getNodeWorkerNumber());
        while (true) {
            try {
                Task task = takeTaskFromQueue(nodeReceiver.getTaskQueue());
                logger.info("Worker " + nodeWorker.getNodeWorkerNumber() +
                        " got task ID " + task.getID() + ", working task for "
                        + task.getLength() + "ms.");
                Thread.sleep(task.getLength());
                logger.info("Worker " + nodeWorker.getNodeWorkerNumber() + " done with task " + task.getID());
            } catch (InterruptedException e) {
                logger.info("Worker " + nodeWorker.getNodeWorkerNumber() + " interrupted, simulation stop");
                break;
            }
        }
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
