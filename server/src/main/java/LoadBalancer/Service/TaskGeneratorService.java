package LoadBalancer.Service;

import LoadBalancer.Model.NodeReceiver;
import LoadBalancer.Model.Task;
import LoadBalancer.Model.TaskGenerator;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static LoadBalancer.Model.GlobalVariables.*;

@Service
public class TaskGeneratorService implements Runnable{

    public void run() {

    }

    public void createTaskGenerator(int nodeCount){
        TaskGenerator taskgenerator = new TaskGenerator(nodeCount, 10,10);
        taskGeneratorList.add(taskgenerator);
    }

    public void deleteTaskGenerator(int nodeNumber){
        int indexToRemove = nodeNumber-1;
        taskGeneratorList.remove(indexToRemove);

        for(int i = indexToRemove; i < taskGeneratorList.size(); i++){
            TaskGenerator taskgenerator = taskGeneratorList.get(i);
            taskgenerator.setTaskGeneratorNumber(taskgenerator.getTaskGeneratorNumber() - 1);
        }
    }

    public void runGenerator(TaskGenerator taskGenerator){
        System.out.println("Spawned TaskGenerator for Node " + taskGenerator.getTaskGeneratorNumber());
        try {
            Thread.sleep(1000); //da se spawnaju workeri i receiveri
        }
        catch(InterruptedException e){
            throw new RuntimeException(e);
        }

        NodeReceiver nodeReciever = findNodeReciever(taskGenerator.getTaskGeneratorNumber());

        while(true){
            int taskID = taskCount.incrementAndGet();
            Task task = generateTask(taskGenerator.getTaskLength());

            sendTaskToQueue(task, nodeReciever.getTaskQueue());
            System.out.println("Generator " + taskGenerator.getTaskGeneratorNumber() + ": spawned task " + taskID);

            try{
                System.out.println("Starting processing task " + taskID);
                Thread.sleep(taskGenerator.getTaskFrequency());
                System.out.println("Finished processing task " + taskID);
            }
            catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }


    public Task generateTask(int taskCount){
        Task task = new Task(taskCount, 3000); //3000 ms za test
        return task;
    }

    public NodeReceiver findNodeReciever(int number) {
        for (NodeReceiver nr : nodeReceiverList) {
            if (nr.getNodeReceiverNumber() == number) {
                return nr;
            }
        }
        return null;
    }

    public void sendTaskToQueue(Task task, BlockingQueue<Task> taskQueue){
        taskQueue.offer(task);
    }
}
