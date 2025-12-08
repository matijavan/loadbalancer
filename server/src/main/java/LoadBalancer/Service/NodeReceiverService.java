package LoadBalancer.Service;

import LoadBalancer.Model.NodeReceiver;
import LoadBalancer.Model.NodeWorker;
import org.springframework.stereotype.Service;

import static LoadBalancer.Model.GlobalVariables.nodeReceiverList;
import static LoadBalancer.Model.GlobalVariables.nodeWorkerList;

@Service
public class NodeReceiverService implements Runnable {
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
}
