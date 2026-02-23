import java.util.Comparator;
import java.util.List;

public class EDFScheduler implements Scheduler{
    @Override
    public Job selectNextJob(List<Job> readyQueue, int currentTime) {
        return readyQueue.stream()
                .min(Comparator.comparingInt(Job::getAbsoluteDeadline))
                .orElse(null);
    }
}
