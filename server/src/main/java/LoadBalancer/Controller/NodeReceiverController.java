package LoadBalancer.Controller;

import LoadBalancer.Service.NodeReceiverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static LoadBalancer.Model.GlobalVariables.nodeCount;

@RestController
@RequestMapping("/nodereceiver")
@RequiredArgsConstructor


@Service
public class NodeReceiverController {
    private final NodeReceiverService nodeReceiverService;

    @RequestMapping("add")
    public ResponseEntity<?> addNodeReceiver(){
        nodeReceiverService.createNodeReceiver(nodeCount);
        return ResponseEntity.ok("created NodeReceiver number" + nodeCount);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteNodeReceiver(){
        nodeReceiverService.deleteNodeReceiver(nodeCount);
        return ResponseEntity.ok("created NodeReceiver number" + nodeCount);
    }
}
