package LoadBalancer.Model;

import LoadBalancer.NodeReceiver;

import static LoadBalancer.Model.GlobalVariables.nodeReceiverList;

public class GlobalFunctions {
    public NodeReceiver findNodeReceiver(int number) {
        for (NodeReceiver nr : nodeReceiverList) {
            if (nr.getNodeReceiverNumber() == number) {
                return nr;
            }
        }
        return null;
    }
}
