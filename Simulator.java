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
            releaseNewJobs();

            // core part, a job is selected based on the chosen algorithm that implements on the Scheduler interface.
            Job currentJob = scheduler.selectNextJob(readyQueue, currentTime);

            if (currentJob != null) {
                currentJob.runFor(1);

                if (currentJob.getRemainingExecutionTime() <= 0){
                    readyQueue.remove(currentJob);
                    System.out.println("Time " + currentTime + ": Job " + currentJob.getTaskId() + " finished");
                }
                // deadline miss
                if (currentTime > currentJob.getAbsoluteDeadline() && currentJob.getRemainingExecutionTime() > 0){
                    System.out.println("Time " + currentTime + " Job " + currentJob.getTaskId() + " missed its deadline");
                }
            }

            currentTime++;
        }
    }
    // updates readyQueue and modifies nextReleaseTime in tasks. New jobs are deployed from tasks according to each task period, arrival time etc.
    private void releaseNewJobs(){
        for (Task task : tasks){
            if (currentTime == task.getNextReleaseTime()){
                Job job = new Job(task, currentTime);
                readyQueue.add(job);
                task.updateNextReleaseTime();
                System.out.println("current time is: " + currentTime + " and Job " + job.getTaskId() + " is added to the readyQueue");
                System.out.println("readyQueue size: " + readyQueue.size());


            }
        }

    }
}
