package models.checkouts;

import common.exceptions.InsufficientPaymentException;
import common.exceptions.InsufficientQuantityException;

import models.cashiers.contracts.Cashier;
import models.products.contracts.Product;

import java.math.BigDecimal;

public class Checkout implements models.checkouts.contracts.Checkout {
    private int id;

    public Checkout(int _id) {
        setId(_id);
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

    // TO DO: Add Client and Cart classes and refactor processPurchase(Cashier cashier, Client client)
    public void processPurchase(Cashier cashier, Product product, int quantity, BigDecimal paymentAmount) {
        BigDecimal totalPrice = product.calculateTotalPrice().multiply(BigDecimal.valueOf(quantity));

        if (quantity >= product.getQuantity()) {
            throw new InsufficientQuantityException(
                    "Insufficient quantity of " + product.getName() +
                    ". Available quantity: " + product.getQuantity() +
                    ", Required quantity: " + quantity);
        }

        if (paymentAmount.compareTo(totalPrice) >= 0) {
            System.out.println("Transaction successful!");
        } else {
            throw new InsufficientPaymentException("Insufficient payment for the purchase.");
        }

        product.setQuantity(product.getQuantity() - quantity);
    }
}
