import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // example developmental testing
        List<Task> tasks = new ArrayList<>();


        Scheduler scheduler = new DMScheduler();

        Simulator simulator = new Simulator(tasks, scheduler, 20);

    }
}
