import core.ShopController;
import core.StartUp;
import core.contracts.Controller;

public class Main {
    public static void main(String[] args) {
        Controller controller = new ShopController();
        StartUp startUp = new StartUp(controller);
        startUp.run();
    }
}