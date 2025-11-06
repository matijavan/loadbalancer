package LoadBalancer.Model;

import LoadBalancer.NodeReceiver;
import LoadBalancer.NodeWorker;
import LoadBalancer.TaskGenerator;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class GlobalVariables {
    public static AtomicInteger taskCount; //used to determine next taskID
    public static LinkedList<NodeReceiver> nodeReceiverList;
    public static LinkedList<NodeWorker> nodeWorkerList;
    public static LinkedList<TaskGenerator> taskGeneratorList;

    public AtomicInteger getTaskCount() {
        return taskCount;
    }
    public void setTaskCount(AtomicInteger taskCount) {
        this.taskCount = taskCount;
    }

    public static LinkedList<NodeReceiver> getNodeReceiverList() {
        return nodeReceiverList;
    }

    public static LinkedList<NodeWorker> getNodeWorkerList() {
        return nodeWorkerList;
    }

    public static LinkedList<TaskGenerator> getTaskGeneratorList() {
        return taskGeneratorList;
    }
}
