import models.carts.Cart;
import models.clients.contracts.Client;
import models.products.FoodProduct;
import models.products.NonFoodProduct;

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

        Client client = new models.clients.Client(1, "George", BigDecimal.valueOf(100));

        client.getCart().addProduct(p1, 10);
        client.getCart().addProduct(p2, 20);

        System.out.println(client.getCart().toString());
    }
}