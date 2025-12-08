package LoadBalancer.Controller;

import LoadBalancer.Model.NodeWorker;
import LoadBalancer.Service.NodeWorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static LoadBalancer.Model.GlobalVariables.nodeCount;

@RestController
@RequiredArgsConstructor
@RequestMapping("/nodeworker")

@Service
public class NodeWorkerController {
    private final NodeWorkerService nodeWorkerService;

    @PostMapping("createnodeworker")
    public ResponseEntity<?> addNodeWorker(){
        NodeWorker nodeWorker = new NodeWorker(nodeCount);
        nodeWorkerService.createNodeWorker(nodeCount);
        return ResponseEntity.ok("Created NodeWorker number" + nodeCount);
    }
    
    @DeleteMapping("deletenodeworker")
    public ResponseEntity<?> deleteNodeWorker(){
        NodeWorker nodeWorker = new NodeWorker(nodeCount);
        nodeWorkerService.deleteNodeWorker(nodeCount);
        return ResponseEntity.ok("Deleted NodeWorker number" + nodeCount);
    }
}
