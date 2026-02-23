//
// represents the periodic process definition */

public class Task {
    int id;
    int arrivalTime; // offset

    int period; // the frequency of releasing new jobs

    int relativeDeadline;
    private int executionTime;  // time that every job will take as running (being processed)

    private int nextReleaseTime;

    public Task(int id, int arrivalTime, int period, int relativeDeadline, int executionTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.period = period;
        this.relativeDeadline = relativeDeadline;
        this.executionTime = executionTime;
        this.nextReleaseTime = arrivalTime;
    }

    public int getPeriod() {
        return period;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public int getNextReleaseTime() {
        return nextReleaseTime;
    }

    public void updateNextReleaseTime(){
        nextReleaseTime += period;
    }
}

