import models.cashiers.Cashier;
import models.clients.contracts.Client;
import models.products.FoodProduct;
import models.products.NonFoodProduct;
import models.receipts.Receipt;
import models.shop.contracts.Shop;
import repositories.ClientRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
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