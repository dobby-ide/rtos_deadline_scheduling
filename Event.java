public class Event {
    private final int time;
    private final EventType type;
    private final Task task;
    private final Job job;

    public Event(int time, EventType type, Task task, Job job) {
        this.time = time;
        this.type = type;
        this.task = task;
        this.job = job;
    }

    // Factory method for RELEASE event
    public static Event createReleaseEvent(int time, Task task) {
        return new Event(time, EventType.RELEASE, task, null);
    }

    // Factory method for COMPLETION event
    public static Event createCompletionEvent(int time, Job job) {
        return new Event(time, EventType.COMPLETION, null, job);
    }
    public int getTime() {
        return time;
    }

    public EventType getType() {
        return type;
    }

    public Task getTask() {
        return task;
    }

    public Job getJob() {
        return job;
    }

    @Override
    public String toString() {
        return "Event{" +
                "time=" + time +
                ", type=" + type +
                ", task=" + (task != null ? task.id : "null") +
                ", job=" + (job != null ? job.getTaskId() : "null") +
                '}';
    }
}
