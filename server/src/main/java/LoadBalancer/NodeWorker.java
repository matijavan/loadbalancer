package LoadBalancer;

import LoadBalancer.Model.Task;

public class NodeWorker implements Runnable{
    private int NodeWorkerNumber;
    private Task taskCurrentlyBeingProcessed;
    @Override
    public void run() {
        while(true){

        }
    }

    public Task takeTaskFromQueue(){
        Task task = taskQueue.remove();
        return task;
    }
}
