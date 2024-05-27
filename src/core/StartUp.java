package core;

import common.messages.OutputMessages;
import core.contracts.Controller;
import models.cashiers.Cashier;
import models.clients.contracts.Client;
import models.products.FoodProduct;
import models.products.NonFoodProduct;
import models.receipts.Receipt;
import models.shop.contracts.Shop;
import repositories.ClientRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;

public class StartUp implements Runnable {
    private final Scanner scanner;
    private final Controller controller;

    public StartUp(Controller _controller) {
        this.controller = _controller;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        StringBuilder sb = new StringBuilder();

        sb.append(" --- Welcome to Shopify! --- \n").append("\n");

        sb.append(" --- Below are all AVAILABLE commands --- \n");

        for (int i = 0; i < OutputMessages.AVAILABLE_COMMANDS.length; i++) {
            sb.append(String.format("%d. %s \n", i + 1, OutputMessages.AVAILABLE_COMMANDS[i]));
        }

        sb.append(" --- Please, enjoy your stay! --- \n");

        System.out.println(sb);

        while (true) {
            String result = null;

            try {
                result = processInput();

                if (result.equals("Stop")) {
                    break;
                }
            } catch (Exception e) {
                result = e.getMessage();
            }

            System.out.println(result);
        }
    }

    private String processInput() throws IOException {
        String input = this.scanner.nextLine();
        String[] tokens = input.split("\\s+");

        String command = tokens[0];
        String result = null;
        String[] data = Arrays.stream(tokens).skip(1).toArray(String[]::new);

        result = switch (command) {
            case "RegisterShop" -> this.controller.registerShop(data);
            case "RemoveShop" -> this.controller.removeShop(data);
            case "AddProductToShop" -> this.controller.addProductToShop(data);
            case "RemoveProductFromShop" -> this.controller.removeProductFromShop(data);
            case "AddCashierToShop" -> this.controller.addCashierToShop(data);
            case "RemoveCashierFromShop" -> this.controller.removeCashierFromShop(data);
            case "AddCheckoutToShop" -> this.controller.addCheckoutToShop(data);
            case "RemoveCheckoutFromShop" -> this.controller.removeCheckoutFromShop(data);
            case "AddClientToShop" -> this.controller.addClientToShop(data);
            case "RemoveClientFromShop" -> this.controller.removeClientFromShop(data);
            case "AddProductToCart" -> this.controller.addProductToCart(data);
            case "RemoveProductFromCart" -> this.controller.removeProductFromCart(data);
            case "ProcessCheckout" -> controller.processCheckout(data);
            case "GetShopInformation" -> controller.getShopInformation(data);
            case "GetAllShops" -> controller.getAllShops();
            case "Stop" -> command;
            default -> result;
        };

        return result;
    }

    private void Example() {
        NonFoodProduct p1 = new NonFoodProduct(
                1,
                "Samsung S22",
                100,
                BigDecimal.valueOf(1000),
                LocalDate.ofYearDay(2025, 4),
                BigDecimal.valueOf(5),
                10,
                BigDecimal.valueOf(5));

        FoodProduct p2 = new FoodProduct(2,
                "Green Apple",
                100,
                BigDecimal.valueOf(1000),
                LocalDate.ofYearDay(2024, 300),
                BigDecimal.valueOf(5),
                10,
                BigDecimal.valueOf(5));

        Shop shop = new models.shop.Shop(1,"Walmart");

        shop.addProduct(p1);
        shop.addProduct(p2);

        Client client = new models.clients.Client(1, "George", BigDecimal.valueOf(60000));

        ClientRepository clientRepository = new ClientRepository();
        clientRepository.add(client);

        client.getCart().addProduct(p1, 10);
        client.getCart().addProduct(p2, 20);

        System.out.println(clientRepository.getById(1));

        shop.addCashier(new Cashier(1, "Lyubo", BigDecimal.valueOf(2000)));
        shop.addCashier(new Cashier(2, "Kriso", BigDecimal.valueOf(2100)));
        shop.addCheckout();
        shop.addCheckout();

        Receipt receipt = shop.processCheckout(client);

        System.out.println(shop);
    }
}
