package LoadBalancer.Controller;

import LoadBalancer.Model.TaskGenerator;
import LoadBalancer.Service.TaskGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static LoadBalancer.Model.GlobalVariables.nodeCount;

@RestController
@RequiredArgsConstructor
@RequestMapping("/taskgenerator")

public class TaskGeneratorController {
    private final TaskGeneratorService taskGeneratorService;

    @PostMapping("add")
    public ResponseEntity<?> addTaskGenerator(){
        taskGeneratorService.createTaskGenerator(nodeCount);
        return ResponseEntity.ok("created TaskGenerator number" + nodeCount);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteTaskGenerator(){
        taskGeneratorService.deleteTaskGenerator(nodeCount);
        return ResponseEntity.ok("deleted TaskGenerator number" + nodeCount);
    }

}
