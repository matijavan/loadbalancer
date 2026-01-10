package LoadBalancer.Service;

import LoadBalancer.Model.NodeReceiver;
import LoadBalancer.Model.Task;
import LoadBalancer.Model.TaskSpawnEvent;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static LoadBalancer.Model.GlobalVariables.taskCount;
import static LoadBalancer.Model.GlobalVariables.taskSpawnEventList;

@Service
public class TaskHistoryService {
    private static final Logger logger = LoggerFactory.getLogger(TaskHistoryService.class);
    private final List<TaskSpawnEvent> taskHistory = Collections.synchronizedList(new LinkedList<>());
    private Thread generatorThread = new Thread();

    public void recordTask(TaskSpawnEvent event) {
        taskHistory.add(event);
    }

    public List<TaskSpawnEvent> getTaskHistory() {
        return Collections.unmodifiableList(taskHistory);
    }

    public void clearTaskHistory() {
        taskHistory.clear();
    }

    public byte[] serializeHistory() throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {

            logger.info("Serializing and saving task history, amount of tasks in this sim: " + taskSpawnEventList.size());
            oos.writeObject(taskSpawnEventList);
            return baos.toByteArray();
        }
    }

    public void loadHistory(InputStream inputStream)
            throws IOException, ClassNotFoundException {

        try (ObjectInputStream ois = new ObjectInputStream(inputStream)) {
            List<TaskSpawnEvent> loaded =
                    (List<TaskSpawnEvent>) ois.readObject();

            taskHistory.clear();
            taskHistory.addAll(loaded);
            logger.info("Simulaton loaded, amount of tasks in this simulation: " + taskHistory.size());
        }
    }

    public void deleteLoadedHistory() {
        taskHistory.clear();
    }

    public void startLoadedGenerationOfTasks(){
        generatorThread = new Thread(() -> runLoadedGenerationOfTasks());
        generatorThread.start();
    }

    public void runLoadedGenerationOfTasks(){
        try {
            Thread.sleep(1000); //da se spawnaju workeri i receiveri
        }
        catch(InterruptedException e){
        }

        logger.info("Spawned generator of loaded tasks");
        logger.info("amount of tasks in this sim: " + taskHistory.size());
        long startSimulationTime = System.nanoTime();
        long previousSpawnTime = 0;
        for(TaskSpawnEvent event : taskHistory){
            try {
                long relativeSpawnTime = event.spawnTime();
                long sleepTimeNanoseconds = relativeSpawnTime - previousSpawnTime;

               Thread.sleep(sleepTimeNanoseconds / 1_000_000,
                      (int) (sleepTimeNanoseconds % 1_000_000));
                Task task = event.task();
                NodeReceiver nr = TaskGeneratorService.findNodeReciever(event.nodeID());
                logger.info("Generator spawned task " + task.getID() + ", sending it to node " + event.nodeID());
                TaskGeneratorService.sendTaskToQueue(task, nr.getTaskQueue());
                previousSpawnTime = relativeSpawnTime;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            logger.info("All tasks from simulation history generated!");

        }
    }

    public void stopLoadedGenerationOfTasks(){
        generatorThread.interrupt();
        generatorThread = null;
        taskCount.set(0);
    }
}
