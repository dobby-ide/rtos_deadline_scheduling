import java.util.ArrayList;
import java.util.List;

// this class is the Orchestrator (pattern) or controller
public class Simulator {

    private int currentTime;
    private final int simulationEndTime;
    private List<Task> tasks;
    private List<Job> readyQueue;
    private Scheduler scheduler;
    private Job runningJob;
    private int missedDeadline;
    private int completedJobs;

    public Simulator(List<Task> tasks, Scheduler scheduler, int simulationEndTime){
        this.tasks= tasks;
        this.scheduler = scheduler;
        this.simulationEndTime = simulationEndTime;

        this.currentTime = 0;
        this.readyQueue = new ArrayList<>();
        this.runningJob = null;

        this.missedDeadline = 0;
        this.completedJobs = 0;
    }

    public void run(){
        while(currentTime < simulationEndTime){

            currentTime++;
        }
    }
}
