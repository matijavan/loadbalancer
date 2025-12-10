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
    public ResponseEntity<?> addTaskGenerator(){
        taskGeneratorService.createTaskGenerator(nodeCount);
        return ResponseEntity.ok("created TaskGenerator number" + nodeCount);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTaskGenerator(@PathVariable int id){
        taskGeneratorService.deleteTaskGenerator(id);
        return ResponseEntity.ok("deleted TaskGenerator number" + id);
    }

    @PostMapping("/frequency/{id}")
    public ResponseEntity<?> frequencyTaskGenerator(@PathVariable int id, @RequestBody TaskFrequencyRequest taskFrequencyRequest){
        int newFrequency = taskFrequencyRequest.getTaskFrequency();
        taskGeneratorService.changeTaskGeneratorFrequency(id, newFrequency);

        return ResponseEntity.ok("updated Task Frequency of node " + id + " to " + newFrequency);
    }

    @PostMapping("/length/{id}")
    public ResponseEntity<?> lengthTaskGenerator(@PathVariable int id, @RequestBody TaskLengthRequest taskLengthRequest){
        int newLength = taskLengthRequest.getTaskLength();
        taskGeneratorService.changeTaskGeneratorLength(id, newLength);
        return ResponseEntity.ok("updated Task Length of node " + id + " to " + newLength);
    }

}
