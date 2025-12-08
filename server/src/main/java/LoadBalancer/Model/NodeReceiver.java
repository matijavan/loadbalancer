package LoadBalancer.Model;

import lombok.Data;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Data
public class NodeReceiver{

    private int NodeReceiverNumber;
    private BlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>();


    public NodeReceiver(int nodeReceiverNumber) {
        NodeReceiverNumber = nodeReceiverNumber;
        BlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>();
    }

}
