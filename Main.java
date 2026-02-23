import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // example developmental testing
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1, 0, 8, 12, 3));   // Task 1
        tasks.add(new Task(2, 0, 10, 20, 4));  // Task 2
        tasks.add(new Task(3, 0, 10, 25, 4));  // Task 3
        tasks.add(new Task(4, 0, 15, 28, 4));  // Task 4
        tasks.add(new Task(5, 0, 20, 32, 3)); // Task 5


        Scheduler rmScheduler = new RMScheduler();
        Scheduler dmScheduler = new DMScheduler();
        Scheduler edfScheduler = new EDFScheduler();

        // run RM
        System.out.println("=== RM Scheduler ===");
        Simulator rmSim = new Simulator(tasks, rmScheduler, 100);
        rmSim.run();
        rmSim.printReport("RM");

        // run DM
        System.out.println("\n=== DM Scheduler ===");
        Simulator dmSim = new Simulator(tasks, dmScheduler,100);
        dmSim.run();
        dmSim.printReport("DM");


        // run EDF
        System.out.println("\n=== EDF Scheduler ===");
        Simulator edfSim = new Simulator(tasks, edfScheduler, 100);
        edfSim.run();
        edfSim.printReport("EDF");


    }
}
