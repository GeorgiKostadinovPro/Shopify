package models;

import common.messages.ExceptionMessages;
import models.contracts.ICashier;
import utilities.DecimalFormatter;

import java.math.BigDecimal;

public class Cashier implements ICashier {
    private int id;
    private String name;
    private BigDecimal monthlySalary;

    public Cashier(int _id, String _name, BigDecimal _monthlySalary) {
        this.setId(_id);
        this.setName(_name);
        this.setMonthlySalary(_monthlySalary);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public BigDecimal getMonthlySalary() {
        return this.monthlySalary;
    }

    @Override
    public String toString() {
        return "Cashier: " + this.name + " (ID: " + this.id + ") has monthly salary: $" + DecimalFormatter.format(this.monthlySalary) + "\n";
    }

    private void setId(int _id) {
        if (_id <= 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_IDENTIFIER);
        }

        this.id = _id;
    }

    private void setName(String _name) {
        if (_name == null || _name.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_NAME);
        }

        this.name = _name;
    }

    private void setMonthlySalary(BigDecimal _monthlySalary) {
        if (_monthlySalary == null || _monthlySalary.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_MONTHLY_SALARY);
        }

        this.monthlySalary = _monthlySalary;
    }
}
