import models.carts.Cart;
import models.cashiers.Cashier;
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

        Cart c1 = new Cart(1);

        c1.addProduct(p1, 10);
        c1.addProduct(p2, 20);

        System.out.println(c1);

        System.out.println(p1);
    }
}