package LoadBalancer.Service;

import LoadBalancer.Model.NodeReceiver;
import LoadBalancer.Model.NodeWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import static LoadBalancer.Model.GlobalVariables.nodeReceiverList;
import static LoadBalancer.Model.GlobalVariables.nodeWorkerList;

@Service
public class NodeReceiverService implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(NodeReceiverService.class);
    private final List<Thread> receiverThreads = new LinkedList<>();
    @Override
    public void run() {}

    public void createNodeReceiver(int nodeCount){
        NodeReceiver nodeReceiver  = new NodeReceiver(nodeCount);
        nodeReceiverList.add(nodeReceiver);
    }

    public void deleteNodeReceiver(int nodeNumber){
        int indexToRemove = nodeNumber-1;
        nodeReceiverList.remove(indexToRemove);

        for(int i = indexToRemove; i < nodeReceiverList.size(); i++){
            NodeReceiver nodeReceiver = nodeReceiverList.get(i);
            nodeReceiver.setNodeReceiverNumber(nodeReceiver.getNodeReceiverNumber() - 1);
        }
    }

    public double getNodeReceiverLoad(int nodeNumber){
        NodeReceiver nodeReceiver = nodeReceiverList.get(nodeNumber);
        double load = (double) nodeReceiver.getTaskQueue().size() / nodeReceiver.getNodeReceiverCapacity();
        double roundedLoad = Math.round(load * 100.0) / 100.0; //da ga zaokruzi na dve decimale
        return roundedLoad;
    }

    public LinkedList<Double> getAllNodeReceiverLoads(){
        LinkedList<Double> loads = new LinkedList<>();
        for(NodeReceiver nodeReceiver : nodeReceiverList){
            loads.add(getNodeReceiverLoad(nodeReceiver.getNodeReceiverNumber()));
        }
        return loads;
    }

    public void changeNodeReceiverCapacity(int nodeNumber, int capacity){
        NodeReceiver nodeReceiver = nodeReceiverList.get(nodeNumber);
        nodeReceiver.setNodeReceiverCapacity(capacity);
    }

    public void startAllReceivers(){
        for (NodeReceiver nr : nodeReceiverList){
            Thread t = new Thread(() -> runReceiver(nr));
            receiverThreads.add(t);
            t.start();
        }
    }

    public void stopAllReceivers(){
        for(Thread t : receiverThreads){
            t.interrupt();
        }

        receiverThreads.clear();
        //CLEARANJE TASKQUEUE NE RADI NE ZNAM ZAŠTO???????????????
        for(NodeReceiver nr : nodeReceiverList){
            nr.getTaskQueue().clear(); //očisti sve taskove, stopali smo simulation pa idemo od nule kad startamo opet
        }
    }

    public void runReceiver(NodeReceiver nodeReceiver){
        logger.info("Spawned receiver for Node " + nodeReceiver.getNodeReceiverNumber());
        while(true){
            //on sad ništa ne radi,
            // on će biti zaslužan kasnije za
            // preraspoređivanje poslova na druge receivere
        }
    }
}
