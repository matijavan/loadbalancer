package LoadBalancer;

import LoadBalancer.Model.Task;

import java.util.Queue;

import static LoadBalancer.Model.GlobalVariables.taskCount;

public class TaskGenerator implements Runnable{
    private int TaskGeneratorNumber; //s ovim brojem će biti povezan s n-tim Nodeom.
    private int taskLength;
    private int taskFrequency;

    @Override
    public void run() {
        while(true){
            int taskID = taskCount.incrementAndGet();
            Task task = generateTask(taskID);
            NodeReciever nodeReciever =
            sendTaskToQueue(task, get);
            try {
                Thread.sleep(3000);
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


}
