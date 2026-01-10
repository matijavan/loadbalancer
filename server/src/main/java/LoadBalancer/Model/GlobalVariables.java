package LoadBalancer.Model;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class GlobalVariables {
    public static AtomicInteger taskCount = new AtomicInteger(); //used to determine next taskID
    public static LinkedList<NodeReceiver> nodeReceiverList = new LinkedList<>();
    public static LinkedList<NodeWorker> nodeWorkerList = new LinkedList<>();
    public static LinkedList<TaskGenerator> taskGeneratorList = new LinkedList<>();
    public static LinkedList<TaskSpawnEvent>  taskSpawnEventList = new LinkedList<>();
    public static int nodeCount = 0;
    public static long startSimulationTime;

    public AtomicInteger getTaskCount() {
        return taskCount;
    }
    public void setTaskCount(AtomicInteger taskCount) {
        this.taskCount = taskCount;
    }

    public static int getNodeCount() {
        return nodeCount;
    }

    public static void setNodeCount(int nodeCount) {
        GlobalVariables.nodeCount = nodeCount;
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
