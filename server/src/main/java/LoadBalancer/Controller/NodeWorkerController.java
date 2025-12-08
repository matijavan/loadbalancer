package LoadBalancer.Controller;

import LoadBalancer.Model.NodeWorker;
import LoadBalancer.Service.NodeWorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static LoadBalancer.Model.GlobalVariables.nodeCount;

@RestController
@RequiredArgsConstructor
@RequestMapping("/nodeworker")

public class NodeWorkerController {
    private final NodeWorkerService nodeWorkerService;

    @PostMapping("/add")
    public ResponseEntity<?> addNodeWorker(){
        NodeWorker nodeWorker = new NodeWorker(nodeCount);
        nodeWorkerService.createNodeWorker(nodeCount);
        return ResponseEntity.ok("Created NodeWorker number" + nodeCount);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNodeWorker(@PathVariable int id){
        nodeWorkerService.deleteNodeWorker(id);
        return ResponseEntity.ok("Deleted NodeWorker number" + id);
    }
}
