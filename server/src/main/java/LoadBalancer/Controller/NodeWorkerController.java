package LoadBalancer.Controller;

import LoadBalancer.Model.NodeWorker;
import LoadBalancer.Service.NodeWorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static LoadBalancer.Model.GlobalVariables.nodeCount;
import static LoadBalancer.Model.GlobalVariables.nodeWorkerList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/nodeworker")

public class NodeWorkerController {
    private final NodeWorkerService nodeWorkerService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllNodeWorkers() {
        System.out.println("Returning " + nodeWorkerList.size() + " nodes");
        return ResponseEntity.ok(nodeWorkerList);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNodeWorker() {
        System.out.println("Adding node number " + nodeCount);
        NodeWorker nodeWorker = new NodeWorker(nodeCount);
        nodeWorkerService.createNodeWorker(nodeCount);
        return ResponseEntity.ok(nodeWorkerList);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNodeWorker(@PathVariable int id) {
        nodeWorkerService.deleteNodeWorker(id);
        return ResponseEntity.ok("Deleted node worker " + id);
    }

    @PostMapping("/startall")
    public ResponseEntity<?> startAllNodeWorkers() {
        nodeWorkerService.startAllWorkers();
        return ResponseEntity.ok("Started all Node Workers");
    }

    @PostMapping("/stopall")
    public ResponseEntity<?> stopAllNodeWorkers() {
        System.out.println("Stopping all workers");
        nodeWorkerService.stopAllWorkers();
        return ResponseEntity.ok("Stopped all Node Workers");
    }
}
