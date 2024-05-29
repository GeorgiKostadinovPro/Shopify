package models.contracts;

import java.math.BigDecimal;

public interface Cashier {
    int getId();
    String getName();
    BigDecimal getMonthlySalary();
}
