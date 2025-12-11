package LoadBalancer.Model;

import lombok.Data;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Data
public class NodeReceiver{

    private int nodeReceiverNumber;
    private BlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>();
    private int nodeReceiverCapacity;


    public NodeReceiver(int nodeReceiverNumber) {
        this.nodeReceiverNumber = nodeReceiverNumber;
        BlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>();
        this.nodeReceiverCapacity = 50; //na pocetku capacity 50 pa se mijenja
    }

}
