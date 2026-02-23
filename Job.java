public class Job {
    Task parentTask;
    int releaseTime;
    int absoluteDeadline;
    int remainingExecutionTime; // while being technically the execution time of a job, it will serve to understand when a job has finished the execution, eventually be decreased for each tick

    public Job(Task parent, int releaseTime) {
        this.parentTask = parent;
        this.releaseTime = releaseTime;
        this.absoluteDeadline = releaseTime + parent.relativeDeadline;
        this.remainingExecutionTime = parent.getExecutionTime();
    }


    // Task parent period is important when Rate Monotonic is chosen as algorithm to decide which job goes first
    public int getTaskPeriod(){
        return this.parentTask.getPeriod();
    }
    public int getTaskId(){
        return parentTask.id;
    }
}
