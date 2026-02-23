import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // example developmental testing
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1, 0, 8, 12, 10)); // executionTime > period
        tasks.add(new Task(2, 0, 10, 20, 15));


        Scheduler scheduler = new RMScheduler();

        Simulator simulator = new Simulator(tasks, scheduler, 20);

        simulator.run();

    }
}
