package LoadBalancer.Service;

import LoadBalancer.Model.NodeReceiver;
import LoadBalancer.Model.NodeWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static LoadBalancer.Model.GlobalVariables.nodeReceiverList;
import static LoadBalancer.Model.GlobalVariables.nodeWorkerList;

@Service
public class NodeReceiverService implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(NodeReceiverService.class);
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

    public void startAllReceivers(){
        for (NodeReceiver nr : nodeReceiverList){
            new Thread(() -> runReceiver(nr)).start();
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
