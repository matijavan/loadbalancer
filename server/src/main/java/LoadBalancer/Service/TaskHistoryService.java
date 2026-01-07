package LoadBalancer.Service;

import LoadBalancer.Model.TaskSpawnEvent;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TaskHistoryService {
    private final List<TaskSpawnEvent> taskHistory = Collections.synchronizedList(new LinkedList<>());

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

            oos.writeObject(taskHistory);
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
        }
    }

}
