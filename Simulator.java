import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

// this class is the Orchestrator (pattern) or controller
public class Simulator {
    private List<Job> missedJobs = new ArrayList<>();
    private Integer firstMissTime = null;
    PriorityQueue<Event> eventQueue;
    private int currentTime;
    private final int simulationEndTime;
    private List<Task> tasks;
    private List<Job> readyQueue;
    private Scheduler scheduler;
    private Job runningJob;
    private List<Job> allJobs = new ArrayList<>();
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
        allJobs.add(job);

        // Schedule next release
        int nextReleaseTime = currentTime + task.getPeriod();
        if (nextReleaseTime <= simulationEndTime) {
            eventQueue.add(Event.createReleaseEvent(nextReleaseTime, task));
        }

        System.out.println("Time " + currentTime + ": Job " + job.getTaskId() + " released");

        if (runningJob == null) {
            scheduleNextJobIfIdle();
            return;
        }

        Job highest = scheduler.selectNextJob(readyQueue, currentTime);
        if (runningJob == null) {
            scheduleNextJobIfIdle();
        } else if (highest != null && highest != runningJob) {
            preempt(highest);
        }
    }

    private void preempt(Job newJob) {
        if (runningJob == null || newJob == null || newJob == runningJob) return;

        // 1. Calculate executed time for current running job
        int executed = currentTime - runningJob.getLastStartTime();
        totalBusyTime += executed;
        runningJob.setRemainingExecutionTime(runningJob.getRemainingExecutionTime() - executed);

        // 2. Remove its completion event
        eventQueue.removeIf(e -> e.getType() == EventType.COMPLETION && e.getJob() == runningJob);

        // 3. Put old job back in ready queue if it still has work
        if (runningJob.getRemainingExecutionTime() > 0 && !runningJob.isFinished()) {
            readyQueue.add(runningJob);
        }

        // 4. Switch to new job
        readyQueue.remove(newJob);
        runningJob = newJob;
        runningJob.setLastStartTime(currentTime);

        // 5. Schedule completion for new job
        eventQueue.add(Event.createCompletionEvent(
                currentTime + runningJob.getRemainingExecutionTime(),
                runningJob
        ));
    }

    private void scheduleNextJobIfIdle() {
        if (runningJob != null || readyQueue.isEmpty()) return;

        Job nextJob = scheduler.selectNextJob(readyQueue, currentTime);
        if (nextJob == null) return;

        readyQueue.remove(nextJob); // remove only once
        runningJob = nextJob;
        runningJob.setLastStartTime(currentTime);

        // Schedule its completion
        eventQueue.add(Event.createCompletionEvent(
                currentTime + runningJob.getRemainingExecutionTime(),
                runningJob
        ));
    }

    private void handleCompletion(Event event) {
        Job job = event.getJob();

        // Skip if this job has already been finished
        if (job.isFinished()) return;
        job.markFinished();

        int executed = currentTime - job.getLastStartTime();
        totalBusyTime += executed;
        System.out.println("total busy time so far: "+totalBusyTime);
        runningJob = null;

        job.setRemainingExecutionTime(0);
        job.addFinishTime(currentTime);

        if (currentTime > job.getAbsoluteDeadline()) {
            if (firstMissTime == null) firstMissTime = currentTime;
            System.out.println("Time " + currentTime + ": Job " + job.getTaskId() + " missed its deadline!");
            missedJobs.add(job);
        } else {
            System.out.println("Time " + currentTime + ": Job " + job.getTaskId() + " finished");
        }
    }


    public void printReport(String schedulerName) {
        System.out.println("\n=== " + schedulerName + " Results ===\n");

        // Determine feasible time: last unit before the first missed deadline
        int feasibleUntil;
        if (missedJobs.isEmpty()) {
            feasibleUntil = simulationEndTime;
        } else {
            feasibleUntil = missedJobs.get(0).getAbsoluteDeadline() - 1;
        }
        System.out.println("Schedule feasible from 0 to " + feasibleUntil + " units.");

        // Show first missed deadline, if any
        if (!missedJobs.isEmpty()) {
            Job firstMissed = missedJobs.get(0);
            System.out.println("At time " + firstMissed.getAbsoluteDeadline() +
                    ", process " + firstMissed.getTaskId() +
                    " did not meet its deadline.");
        }

        // Show total CPU usage
        System.out.println("CPU time used: " + totalBusyTime + " units.\n");

        // Per-task summary in assignment format
        for (Task task : tasks) {
            // Collect finish times of all jobs for this task
            List<Integer> finishes = allJobs.stream()
                    .filter(job -> job.getParentTask() == task)
                    .map(Job::getFinishTimes)
                    .flatMap(List::stream)
                    .toList();

            System.out.println("Process " + task.id +
                    ": Arrival Time: " + task.getArrivalTime() +
                    ", Service: " + task.getExecutionTime() +
                    ", Period: " + task.getPeriod() +
                    ", finish: " + finishes);
        }
    }
}
