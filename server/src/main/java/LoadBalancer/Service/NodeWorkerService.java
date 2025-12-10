package LoadBalancer.Service;

import LoadBalancer.Model.NodeReceiver;
import LoadBalancer.Model.NodeWorker;
import LoadBalancer.Model.Task;
import LoadBalancer.Model.TaskGenerator;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;

import static LoadBalancer.Model.GlobalVariables.*;

@Service
public class NodeWorkerService implements Runnable{
    @Override
    public void run() {

    }

    public void createNodeWorker(int nodeCount){
        NodeWorker nodeworker = new NodeWorker(nodeCount);
        nodeWorkerList.add(nodeworker);
        setNodeCount(nodeCount + 1); //nodeworker se zadnji kreira pa onda inkrementiram nodeCount
    }

    public void deleteNodeWorker(int nodeNumber){
        int indexToRemove = nodeNumber-1;
        nodeWorkerList.remove(indexToRemove);

        for(int i = indexToRemove; i < nodeWorkerList.size(); i++){
            NodeWorker nodeWorker = nodeWorkerList.get(i);
            nodeWorker.setNodeWorkerNumber(nodeWorker.getNodeWorkerNumber() - 1);
        }

        setNodeCount(nodeCount - 1);
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
