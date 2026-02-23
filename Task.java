//
// represents the periodic process definition */

public class Task {
    int id;
    int arrivalTime;

    int period; // the frequency of releasing new jobs

    int relativeDeadline;
    int executionTime;

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
}

