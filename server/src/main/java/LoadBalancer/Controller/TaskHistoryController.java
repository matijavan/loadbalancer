package LoadBalancer.Controller;

import LoadBalancer.Service.TaskHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/taskhistory")

public class TaskHistoryController {

    @Autowired
    private final TaskHistoryService taskHistoryService;

    @GetMapping("/save")
    public ResponseEntity<byte[]> saveHistory() throws IOException {
        byte[] fileBytes = taskHistoryService.serializeHistory();
        System.out.println("Saved file");
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=task_history.ser")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileBytes);
    }

    @PostMapping("/load")
    public ResponseEntity<Void> loadHistory(@RequestParam("file") MultipartFile file) throws IOException, ClassNotFoundException {
        taskHistoryService.loadHistory(file.getInputStream());
        System.out.println("Loaded file: " +  file.getOriginalFilename());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteHistory() throws IOException, ClassNotFoundException {
        taskHistoryService.deleteLoadedHistory();
        System.out.println("Deleted loaded file");
        return ResponseEntity.ok().build();
    }
}
