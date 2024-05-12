package models.carts;

public class CartItem {
    private int id;
    private int cartId;
    private int productId;
    private int quantity;

    public CartItem(int _id, int _cartId, int _productId, int _quantity) {
        setId(_id);
        setCartId(_cartId);
        setProductId(_productId);
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

    public int getProductId() {
        return this.productId;
    }

    public void setProductId(int _productId) {
        if (_productId <= 0) {
            throw new IllegalArgumentException("ProductId must be greater than 0.");
        }

        this.productId = _productId;
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
}
