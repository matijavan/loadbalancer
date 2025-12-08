package LoadBalancer.Controller;

import LoadBalancer.Service.NodeReceiverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static LoadBalancer.Model.GlobalVariables.nodeCount;

@RestController
@RequestMapping("/nodereceiver")
@RequiredArgsConstructor

public class NodeReceiverController {
    private final NodeReceiverService nodeReceiverService;

    @PostMapping("/add")
    public ResponseEntity<?> addNodeReceiver(){
        nodeReceiverService.createNodeReceiver(nodeCount);
        return ResponseEntity.ok("created NodeReceiver number" + nodeCount);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNodeReceiver(@PathVariable int id){
        nodeReceiverService.deleteNodeReceiver(id);
        return ResponseEntity.ok("deleted NodeReceiver number" + id);
    }
}
