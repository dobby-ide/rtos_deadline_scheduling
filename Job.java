public class Job {
    Task parentTask;
    int releaseTime;
    int absoluteDeadline;
    int remainingExecutionTime;

    public Job(Task parent, int releaseTime) {
        this.parentTask = parent;
        this.releaseTime = releaseTime;
        this.absoluteDeadline = releaseTime + parent.relativeDeadline;
        this.remainingExecutionTime = parent.executionTime;
    }


    // Task parent period is important when Rate Monotonic is chosen as algorithm to decide which job goes first
    public int getTaskPeriod(){
        return this.parentTask.getPeriod();
    }
    public int getTaskId(){
        return parentTask.id;
    }
}
