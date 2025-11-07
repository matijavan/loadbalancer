package LoadBalancer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static LoadBalancer.Model.GlobalVariables.*;

@SpringBootTest
class LoadBalancerApplicationTests {


	public static void main(String[] args) {
		TaskGenerator taskGenerator = new TaskGenerator(1, 3000, 1000);
		taskGeneratorList.add(taskGenerator);
		Thread taskGeneratorThread = new Thread(taskGenerator);

		NodeReceiver nodeReceiver = new NodeReceiver(1);
		nodeReceiverList.add(nodeReceiver);
		Thread nodeReceiverThread = new Thread(nodeReceiver);

		NodeWorker nodeWorker = new NodeWorker(1);
		nodeWorkerList.add(nodeWorker);
		Thread nodeWorkerThread = new Thread(nodeWorker);

		taskGeneratorThread.start();
		nodeReceiverThread.start();
		nodeWorkerThread.start();
	}
}
