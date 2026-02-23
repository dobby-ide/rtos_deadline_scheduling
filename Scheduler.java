import java.util.List;

public interface Scheduler {
    Job selectNextJob(List<Job> readyQueue, int currentTime);

}
