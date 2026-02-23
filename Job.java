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

    public int getTaskId(){
        return parentTask.id;
    }
}
