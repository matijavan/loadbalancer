package LoadBalancer;

import LoadBalancer.Model.Task;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;

import static LoadBalancer.Model.GlobalVariables.nodeReceiverList;
import static LoadBalancer.Model.GlobalVariables.taskCount;

public class TaskGenerator implements Runnable{
    private int TaskGeneratorNumber; //s ovim brojem će biti povezan s n-tim Nodeom.
    private int taskLength;
    private int taskFrequency;

    @Override
    public void run() {
        System.out.println("Rodio se taskgenerator od Node " + getTaskGeneratorNumber());
        try {
            Thread.sleep(1000); //potrebno da se kreiraju prvo svi workeri i recieveri i generaotri
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while(true){
            int taskID = taskCount.incrementAndGet();
            Task task = generateTask(taskID);
            NodeReceiver nodeReceiver = findNodeReciever(TaskGeneratorNumber);
            sendTaskToQueue(task, nodeReceiver.getTaskQueue());
            System.out.println("Generator " + getTaskGeneratorNumber() + ": spawnao sam task " + taskID);
            try {
                Thread.sleep(getTaskFrequency());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public TaskGenerator(int taskGeneratorNumber, int taskLength, int taskFrequency) {
        TaskGeneratorNumber = taskGeneratorNumber;
        this.taskLength = taskLength;
        this.taskFrequency = taskFrequency;
    }

    public int getTaskGeneratorNumber() {
        return TaskGeneratorNumber;
    }

    public void setTaskGeneratorNumber(int taskGeneratorNumber) {
        TaskGeneratorNumber = taskGeneratorNumber;
    }

    public int getTaskLength() {
        return taskLength;
    }

    public void setTaskLength(int taskLength) {
        this.taskLength = taskLength;
    }

    public int getTaskFrequency() {
        return taskFrequency;
    }

    public void setTaskFrequency(int taskFrequency) {
        this.taskFrequency = taskFrequency;
    }

    public Task generateTask(int taskCount){
        Task task = new Task(taskCount, taskLength);
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
