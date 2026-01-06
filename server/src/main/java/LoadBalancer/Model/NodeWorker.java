package LoadBalancer.Model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.concurrent.BlockingQueue;

import static LoadBalancer.Model.GlobalVariables.nodeReceiverList;

@Data
public class NodeWorker{

    @NotNull
    private int NodeWorkerNumber;
    private Task taskCurrentlyBeingProcessed;

    public NodeWorker(int nodeWorkerNumber) {
        NodeWorkerNumber = nodeWorkerNumber;
    }

}
