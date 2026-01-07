package LoadBalancer.Controller;

import LoadBalancer.DTO.CapacityRequest;
import LoadBalancer.Service.NodeReceiverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

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

    @PostMapping("/startall")
    public ResponseEntity<?> startAllNodeReceivers(){
        System.out.println("Starting all Node Receivers");
        nodeReceiverService.startAllReceivers();
        return ResponseEntity.ok("Started all Node Receivers");
    }

    @PostMapping("/stopall")
    public ResponseEntity<?> stopAllNodeReceivers(){
        System.out.println("Stopping all Node Receivers");
        nodeReceiverService.stopAllReceivers();
        return ResponseEntity.ok("Stopped all Node Receivers");
    }

    @PostMapping("/capacity")
    public ResponseEntity<?> changeNodeReceiverCapacity(@RequestBody CapacityRequest capacityRequest){
        int nodeNumber = capacityRequest.getNodeNumber();
        int newCapacity = capacityRequest.getCapacity();
        System.out.println("Node: " + nodeNumber + " Capacity: " + newCapacity);
        nodeReceiverService.changeNodeReceiverCapacity(nodeNumber, newCapacity);
        return ResponseEntity.ok("changed NodeReceiver " + nodeNumber + " capacity" + "to " + newCapacity);
    }
    @GetMapping("/load")
    public ResponseEntity<?> loadNodeReceiver(){
        LinkedList<Double> loads =  nodeReceiverService.getAllNodeReceiverLoads();
        return ResponseEntity.ok(loads);
    }
}
