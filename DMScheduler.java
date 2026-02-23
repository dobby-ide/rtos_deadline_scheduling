import java.util.Comparator;
import java.util.List;

public class DMScheduler implements Scheduler{
    @Override
    public Job selectNextJob(List<Job> readyQueue, int currentTime) {
        return readyQueue.stream()
                .min(Comparator.comparingInt(job -> job.getTaskRelativeDeadline()))
                .orElse(null);
    }
}
