package LoadBalancer;

import LoadBalancer.Model.NodeReceiver;
import LoadBalancer.Model.NodeWorker;
import LoadBalancer.Model.TaskGenerator;
import org.springframework.boot.test.context.SpringBootTest;

import static LoadBalancer.Model.GlobalVariables.*;

@SpringBootTest
class LoadBalancerApplicationTests {


	public static void main(String[] args) {
		TaskGenerator taskGenerator = new TaskGenerator(1, 3000, 2500);
		taskGeneratorList.add(taskGenerator);
		Thread taskGeneratorThread = new Thread(taskGenerator);

		NodeReceiver nodeReceiver = new NodeReceiver(1);
		nodeReceiverList.add(nodeReceiver);
		Thread nodeReceiverThread = new Thread(nodeReceiver);

		NodeWorker nodeWorker = new NodeWorker(1);
		nodeWorkerList.add(nodeWorker);
		Thread nodeWorkerThread = new Thread(nodeWorker);

		TaskGenerator taskGenerator2 = new TaskGenerator(2, 5000, 3000);
		taskGeneratorList.add(taskGenerator2);
		Thread taskGeneratorThread2 = new Thread(taskGenerator2);

		NodeReceiver nodeReceiver2 = new NodeReceiver(2);
		nodeReceiverList.add(nodeReceiver2);
		Thread nodeReceiverThread2 = new Thread(nodeReceiver2);

		NodeWorker nodeWorker2 = new NodeWorker(2);
		nodeWorkerList.add(nodeWorker2);
		Thread nodeWorkerThread2 = new Thread(nodeWorker2);

		taskGeneratorThread.start();
		nodeReceiverThread.start();
		nodeWorkerThread.start();

		taskGeneratorThread2.start();
		nodeReceiverThread2.start();
		nodeWorkerThread2.start();


	}
}
