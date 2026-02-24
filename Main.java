import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // example developmental testing
        // CPU utilization is slightly below 1, no overload
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1, 0, 4, 11, 1));
        tasks.add(new Task(2, 0, 6, 10, 2));
        tasks.add(new Task(3, 0, 38, 10, 3));
        tasks.add(new Task(4, 0, 82, 12, 4));
        tasks.add(new Task(5, 0, 55, 56, 6));

        Scheduler rmScheduler = new RMScheduler();
        Scheduler dmScheduler = new DMScheduler();
        Scheduler edfScheduler = new EDFScheduler();

        // run RM
        System.out.println("=== RM Scheduler ===");
        Simulator rmSim = new Simulator(tasks, rmScheduler, 620);
        rmSim.run();
        rmSim.printReport("RM");

        // run DM
        System.out.println("\n=== DM Scheduler ===");
        Simulator dmSim = new Simulator(tasks, dmScheduler, 620);
        dmSim.run();
        dmSim.printReport("DM");


        // run EDF
        System.out.println("\n=== EDF Scheduler ===");
        Simulator edfSim = new Simulator(tasks, edfScheduler, 620);
        edfSim.run();
        edfSim.printReport("EDF");


    }
}
