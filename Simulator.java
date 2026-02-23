import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

// this class is the Orchestrator (pattern) or controller
public class Simulator {
    PriorityQueue<Event> eventQueue;
    private int currentTime;
    private final int simulationEndTime;
    private List<Task> tasks;
    private List<Job> readyQueue;
    private Scheduler scheduler;
    private Job runningJob;
    //private int missedDeadline;
    //private int completedJobs;
    private int totalBusyTime;

    public Simulator(List<Task> tasks, Scheduler scheduler,int simulationEndTime){
        this.tasks= tasks;
        this.scheduler = scheduler;
        this.readyQueue = new ArrayList<>();
        this.eventQueue = new PriorityQueue<>();
        this.currentTime = 0;
        this.runningJob = null;
        this.simulationEndTime = simulationEndTime;

        // schedule initial release event for each task
        for (Task task: tasks){
            Event releaseEvent = new Event(task.getArrivalTime(), EventType.RELEASE, task, null);
            eventQueue.add(releaseEvent);
        }
    }

    public void run() {
        while (!eventQueue.isEmpty() && currentTime <= simulationEndTime) {
            Event event = eventQueue.poll();
            currentTime = event.getTime();

            switch (event.getType()) {
                case RELEASE:
                    handleRelease(event);
                    break;

                case COMPLETION:
                    handleCompletion(event);
                    break;
            }

            scheduleNextJobIfIdle();
        }

        System.out.println("Simulation finished at time " + currentTime);
    }

    private void handleRelease(Event event) {
        Task task = event.getTask();
        Job job = new Job(task, currentTime);
        readyQueue.add(job);

        // Schedule next release only if it is within simulation time
        int nextReleaseTime = currentTime + task.getPeriod();
        if (nextReleaseTime <= simulationEndTime) {
            eventQueue.add(Event.createReleaseEvent(nextReleaseTime, task));
        }

        System.out.println("Time " + currentTime + ": Job " + job.getTaskId() + " released");
    }
    private void scheduleNextJobIfIdle() {
        if (runningJob != null || readyQueue.isEmpty()) return;

        runningJob = scheduler.selectNextJob(readyQueue, currentTime);
        if (runningJob != null) {
            eventQueue.add(Event.createCompletionEvent(currentTime + runningJob.getRemainingExecutionTime(), runningJob));
        }
    }

    private void handleCompletion(Event event) {
        Job job = event.getJob();
        readyQueue.remove(job);
        runningJob = null;

        totalBusyTime += job.getRemainingExecutionTime();

        if (currentTime > job.getAbsoluteDeadline()) {
            System.out.println("Time " + currentTime + ": Job " + job.getTaskId() + " missed its deadline!");
        } else {
            System.out.println("Time " + currentTime + ": Job " + job.getTaskId() + " finished");
        }
    }
    // updates readyQueue and modifies nextReleaseTime in tasks. New jobs are deployed from tasks according to each task period, arrival time etc.
/*    private void releaseNewJobs(){
        for (Task task : tasks){
            if (currentTime == task.getNextReleaseTime()){
                Job job = new Job(task, currentTime);
                readyQueue.add(job);
                task.updateNextReleaseTime();
                System.out.println("current time is: " + currentTime + " and Job " + job.getTaskId() + " is added to the readyQueue");
                System.out.println("readyQueue size: " + readyQueue.size());


            }
        }

    }*/
}
