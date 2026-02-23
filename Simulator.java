import java.util.List;

// this class is the Orchestrator (pattern) or controller
public class Simulator {

    private int currentTime;
    private int simulationEndTime;
    private List<Task> tasks;
    private List<Job> readyQueue;
    private Scheduler scheduler;
    private Job runningJob;
    private int missedDeadline;
    private int completedJobs;
    
}
