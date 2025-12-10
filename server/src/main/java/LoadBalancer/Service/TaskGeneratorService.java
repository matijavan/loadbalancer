package LoadBalancer.Service;

import LoadBalancer.Model.NodeReceiver;
import LoadBalancer.Model.Task;
import LoadBalancer.Model.TaskGenerator;
import org.springframework.stereotype.Service;

import java.util.Random;
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
        int indexToRemove = nodeNumber - 1;
        taskGeneratorList.remove(indexToRemove);

        for(int i = indexToRemove; i < taskGeneratorList.size(); i++){
            TaskGenerator taskgenerator = taskGeneratorList.get(i);
            taskgenerator.setTaskGeneratorNumber(taskgenerator.getTaskGeneratorNumber() - 1);
        }
    }

    public void changeTaskGeneratorFrequency(int nodeID, int newFrequency){
        int indexToChange = nodeID - 1;
        TaskGenerator taskgenerator = taskGeneratorList.get(indexToChange);
        taskgenerator.setTaskFrequency(newFrequency);
    }

    public void changeTaskGeneratorLength(int nodeID, int newLength){
        int indexToChange = nodeID - 1;
        TaskGenerator taskgenerator = taskGeneratorList.get(indexToChange);
        taskgenerator.setTaskLength(newLength);
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
            Task task = generateTask(taskID, taskGenerator.getTaskLength());

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


    public Task generateTask(int taskCount, int taskLength){
        Random rand = new Random();
        int randTaskLength = taskLength +=rand.nextInt(101) - 50;
        Task task = new Task(taskCount, randTaskLength); //3000 ms za test
        return task;
    }

    public int generateSleepLength(int taskFrequency){
        Random rand = new Random();
        int randSleepLength = rand.nextInt(101) - 50 + taskFrequency;
        return randSleepLength;
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
