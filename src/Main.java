import core.ControllerImpl;
import core.StartUp;
import core.contracts.Controller;

public class Main {
    public static void main(String[] args) {
        Controller controller = new ControllerImpl();
        StartUp startUp = new StartUp(controller);
        startUp.run();
    }
}