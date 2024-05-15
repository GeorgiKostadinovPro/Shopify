package models.clients;

import common.exceptions.InsufficientPaymentException;
import models.carts.Cart;
import utilities.DecimalFormatter;

import java.math.BigDecimal;

public class Client implements models.clients.contracts.Client {
    private int id;
    private String name;
    private BigDecimal budget;
    private final Cart cart;

    public Client(int _id, String _name, BigDecimal _budget) {
        setId(_id);
        setName(_name);
        setBudget(_budget);

        this.cart = new Cart(this.id);
    }

    public int getId() {
        return id;
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

    public BigDecimal getBudget() {
        return budget;
    }

    private void setBudget(BigDecimal _budget) {
        if (_budget == null || _budget.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Budget must be greater than 0.");
        }

        this.budget = _budget;
    }

    public Cart getCart() {
        return this.cart;
    }

    public void reduceBudget(BigDecimal _amount) {
        if (this.budget.compareTo(_amount) < 0) {
            throw new InsufficientPaymentException("Your budget is way too low for payment!");
        }

        this.budget = this.budget.subtract(_amount);
    }

    @Override
    public String toString() {
        return "Client [id: " + this.id + ", name: " + this.name + ", budget: $" + DecimalFormatter.format(this.budget) + "]";
    }
}
