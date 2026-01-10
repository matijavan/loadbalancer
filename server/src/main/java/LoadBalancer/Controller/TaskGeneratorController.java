package LoadBalancer.Controller;

import LoadBalancer.DTO.TaskFrequencyRequest;
import LoadBalancer.DTO.TaskLengthRequest;
import LoadBalancer.Model.TaskGenerator;
import LoadBalancer.Service.TaskGeneratorService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static LoadBalancer.Model.GlobalVariables.nodeCount;

@RestController
@RequiredArgsConstructor
@RequestMapping("/taskgenerator")

public class TaskGeneratorController {
    private final TaskGeneratorService taskGeneratorService;

    @PostMapping("/add")
    public ResponseEntity<?> addTaskGenerator() {
        taskGeneratorService.createTaskGenerator(nodeCount);
        return ResponseEntity.ok("created TaskGenerator number" + nodeCount);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTaskGenerator(@PathVariable int id) {
        taskGeneratorService.deleteTaskGenerator(id);
        return ResponseEntity.ok("deleted TaskGenerator number" + id);
    }

    @PostMapping("/frequency")
    public ResponseEntity<?> frequencyTaskGenerator(@RequestBody TaskFrequencyRequest taskFrequencyRequest) {
        int nodeNumber = taskFrequencyRequest.getNodeNumber();
        int newFrequency = taskFrequencyRequest.getFrequency();
        System.out.println("Node: " + nodeNumber + ", Frequency: " + newFrequency);
        taskGeneratorService.changeTaskGeneratorFrequency(nodeNumber, newFrequency);
        return ResponseEntity.ok("updated Task Frequency of node " + nodeNumber + " to " + newFrequency);
    }

    @PostMapping("/length")
    public ResponseEntity<?> lengthTaskGenerator(@RequestBody TaskLengthRequest taskLengthRequest) {
        int nodeNumber = taskLengthRequest.getNodeNumber();
        int newLength = taskLengthRequest.getLength();
        System.out.println("Node: " + nodeNumber + ", Length: " + newLength);
        taskGeneratorService.changeTaskGeneratorLength(nodeNumber, newLength);
        return ResponseEntity.ok("updated Task Length of node " + nodeNumber + " to " + newLength);
    }

    @PostMapping("/startall")
    public ResponseEntity<?> startAllTaskGenerators() {
        System.out.println("Starting all Task Generators");
        taskGeneratorService.startAllTaskGenerators();
        return ResponseEntity.ok("Started all Task Generators");
    }

    @PostMapping("/stopall")
    public ResponseEntity<?> stopAllTaskGenerators() {
        System.out.println("Stopping all Task Generators");
        taskGeneratorService.stopAllTaskGenerators();
        return ResponseEntity.ok("Stopped all Task Generators");
    }
}
