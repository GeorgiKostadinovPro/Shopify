package models.carts;

import models.products.contracts.Product;
import utilities.DecimalFormatter;

import java.math.BigDecimal;

public class CartItem {
    private int id;
    private int cartId;
    private Product product;
    private int quantity;

    public CartItem(int _id, int _cartId, Product _product, int _quantity) {
        setId(_id);
        setCartId(_cartId);
        setProduct(_product);
        setQuantity(_quantity);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int _id) {
        if (_id <= 0) {
            throw new IllegalArgumentException("Id must be greater than 0.");
        }

        this.id = _id;
    }

    public int getCartId() {
        return this.cartId;
    }

    public void setCartId(int _cartId) {
        if (_cartId <= 0) {
            throw new IllegalArgumentException("CartId must be greater than 0.");
        }

        this.cartId = _cartId;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product _product) {
        if (_product == null) {
            throw new IllegalArgumentException("Product must be existing!");
        }

        this.product = _product;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int _quantity) {
        if (_quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }

        this.quantity = _quantity;
    }

    public BigDecimal getItemPrice() {
        return this.product.calculateTotalPrice()
                .multiply(BigDecimal.valueOf(this.getQuantity()));
    }

    @Override
    public String toString() {
        return "ItemId: " + this.id + " -> Product: "
                + this.product.getName() + " | Quantity: "
                + this.quantity + " | Total Price: $" + DecimalFormatter.format(getItemPrice());
    }
}
