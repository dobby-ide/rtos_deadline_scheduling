import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // example developmental testing
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1, 0, 8, 12, 5));   // Task 1
        tasks.add(new Task(2, 0, 10, 20, 7));  // Task 2
        tasks.add(new Task(3, 0, 10, 25, 6));  // Task 3
        tasks.add(new Task(4, 0, 15, 28, 8));  // Task 4
        tasks.add(new Task(5, 0, 20, 32, 10)); // Task 5


        Scheduler scheduler = new DMScheduler();

        Simulator simulator = new Simulator(tasks, scheduler, 500);

        simulator.run();

    }
}
