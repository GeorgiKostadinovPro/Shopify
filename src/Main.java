
import models.cashiers.Cashier;
import models.products.FoodProduct;
import models.products.NonFoodProduct;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        NonFoodProduct p1 = new NonFoodProduct(
                1,
                "Smasung S22",
                BigDecimal.valueOf(1000),
                LocalDate.ofYearDay(2025, 4),
                BigDecimal.valueOf(5),
                10,
                BigDecimal.valueOf(5));

        System.out.println(p1);

        FoodProduct p2 = new FoodProduct(1,
                "Smasung S22+",
                BigDecimal.valueOf(1000),
                LocalDate.ofYearDay(2024, 126),
                BigDecimal.valueOf(5),
                10,
                BigDecimal.valueOf(5));

        System.out.println(p2);

        Cashier c1 = new Cashier(1, "Georgi", BigDecimal.valueOf(2500));

        System.out.println(c1);
    }
}