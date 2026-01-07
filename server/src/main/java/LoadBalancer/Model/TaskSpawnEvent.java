package LoadBalancer.Model;

import java.io.Serializable;

public record TaskSpawnEvent(
        Task task,
        int nodeID,
        long spawnTime
) implements Serializable {}
