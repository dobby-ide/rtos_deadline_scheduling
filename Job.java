import java.util.ArrayList;
import java.util.List;

public class Job {
    Task parentTask;
    int releaseTime;
    int absoluteDeadline;
    int remainingExecutionTime; // while being technically the execution time of a job, it will serve to understand when a job has finished the execution, eventually be decreased for each tick

    private int lastStartTime;
    private List<Integer> finishTimes = new ArrayList<>();

    private boolean finished = false;

    public Job(Task parent, int releaseTime) {
        this.parentTask = parent;
        this.releaseTime = releaseTime;
        this.absoluteDeadline = releaseTime + parent.relativeDeadline;
        this.remainingExecutionTime = parent.getExecutionTime();
        this.lastStartTime = releaseTime;
    }


    public void markFinished() {
        finished = true;
    }

    public boolean isFinished() {
        return finished;
    }
    public void addFinishTime(int finishTime) {
        finishTimes.add(finishTime);
    }

    // Task parent period is important when Rate Monotonic is chosen as algorithm to decide which job goes first
    public int getTaskPeriod(){
        return this.parentTask.getPeriod();
    }
    public int getTaskRelativeDeadline(){
        return this.parentTask.getRelativeDeadline();
    }
    public int getTaskId(){
        return parentTask.id;
    }

    public int getRemainingExecutionTime() {
        return remainingExecutionTime;
    }
    public void setRemainingExecutionTime(int remainingExecutionTime) {
        this.remainingExecutionTime = remainingExecutionTime;
    }
    public void setLastStartTime(int lastStartTime) {
        this.lastStartTime = lastStartTime;
    }

    public int getLastStartTime() {
        return lastStartTime;
    }

    public int getAbsoluteDeadline() {
        return absoluteDeadline;
    }

    public Task getParentTask() {
        return parentTask;
    }

    public List<Integer> getFinishTimes() {
        return new ArrayList<>(finishTimes); // return a copy to avoid external modification
    }
}
