import java.util.Comparator;
import java.util.List;
//Rate Monotonic allows first the jobs where its parent process (Task) has a lower frequency (period) for releasing new jobs
public class RMScheduler implements Scheduler{
    @Override
    public Job selectNextJob(List<Job> readyQueue, int currentTime) {

        return readyQueue.stream()
                .min(Comparator.comparingInt(job -> job.getTaskPeriod()))
                .orElse(null);
    }
}
