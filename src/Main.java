import core.ShopifyController;
import core.StartUp;
import core.contracts.Controller;

public class Main {
    public static void main(String[] args) {
        Controller controller = new ShopifyController();
        StartUp startUp = new StartUp(controller);
        startUp.run();
    }
}