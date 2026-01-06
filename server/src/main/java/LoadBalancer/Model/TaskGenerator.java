package LoadBalancer.Model;

import lombok.Data;

import java.util.concurrent.BlockingQueue;

import static LoadBalancer.Model.GlobalVariables.nodeReceiverList;
import static LoadBalancer.Model.GlobalVariables.taskCount;

@Data
public class TaskGenerator{
    private int TaskGeneratorNumber; //s ovim brojem će biti povezan s n-tim Nodeom.
    private int taskLength;
    private int taskFrequency;

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
}
