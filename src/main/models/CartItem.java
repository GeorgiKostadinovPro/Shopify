package models;

import common.messages.ExceptionMessages;
import models.contracts.IProduct;
import utilities.DecimalFormatter;

import java.math.BigDecimal;

public class CartItem {
    private int id;
    private IProduct product;
    private int quantity;

    public CartItem(int _id, IProduct _product, int _quantity) {
        this.setId(_id);
        this.setProduct(_product);
        this.setQuantity(_quantity);
    }

    public int getId() {
        return this.id;
    }

    public IProduct getProduct() {
        return this.product;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int _quantity) {
        if (_quantity <= 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_PRODUCT_QUANTITY);
        }

        this.quantity = _quantity;
    }

    public BigDecimal getItemTotalPrice() {
        return this.product.calculateFinalPrice()
                .multiply(BigDecimal.valueOf(this.getQuantity()));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("- ")
                .append(this.getProduct().getName()).append(" (")
                .append(this.getQuantity()).append(" x $")
                .append(DecimalFormatter.format(this.getProduct().calculateFinalPrice())).append(" = $")
                .append(DecimalFormatter.format(this.getItemTotalPrice())).append(") ");

        return sb.toString().trim();
    }

    private void setId(int _id) {
        if (_id <= 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_IDENTIFIER);
        }

        this.id = _id;
    }

    private void setProduct(IProduct _product) {
        if (_product == null) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_PRODUCT);
        }

        this.product = _product;
    }
}
