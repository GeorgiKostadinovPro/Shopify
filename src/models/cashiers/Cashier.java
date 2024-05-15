package models.cashiers;

import utilities.DecimalFormatter;

import java.math.BigDecimal;

public class Cashier implements models.cashiers.contracts.Cashier {
    private int id;
    private String name;
    private BigDecimal monthlySalary;

    public Cashier(int _id, String _name, BigDecimal _monthlySalary) {
        this.setId(_id);
        this.setName(_name);
        this.setMonthlySalary(_monthlySalary);
    }

    public int getId() {
        return this.id;
    }

    private void setId(int _id) {
        if (_id <= 0) {
            throw new IllegalArgumentException("Id must be greater than 0.");
        }

        this.id = _id;
    }

    public String getName() {
        return this.name;
    }

    private void setName(String _name) {
        if (_name == null || _name.isEmpty()) {
            throw new IllegalArgumentException("Name must NOT be empty.");
        }

        this.name = _name;
    }

    public BigDecimal getMonthlySalary() {
        return this.monthlySalary;
    }

    public void setMonthlySalary(BigDecimal _monthlySalary) {
        if (_monthlySalary == null || _monthlySalary.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Monthly salary must be greater than zero.");
        }

        this.monthlySalary = _monthlySalary;
    }

    @Override
    public String toString() {
        return "Cashier: " + this.name + " (ID: " + this.id + ") has monthly salary: $" + DecimalFormatter.format(this.monthlySalary) + "\n";
    }
}
