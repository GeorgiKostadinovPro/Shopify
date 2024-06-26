package common.messages;

public class OutputMessages {
    // Shop
    public static final String SUCCESSFULLY_REGISTERED_SHOP = "Successfully registered shop - %s in the system.";
    public static final String NO_SHOP_REGISTERED = "There are NOT any shop registered!";
    public static final String SUCCESSFULLY_REMOVED_SHOP = "Successfully removed shop - %s from the system.";

    // Client
    public static final String SUCCESSFULLY_REGISTERED_CLIENT = "Successfully added client - %s";
    public static final String SUCCESSFULLY_REMOVED_CLIENT = "Successfully removed client with Id=%d.";

    // Cart
    public static final String SUCCESSFULLY_ADDED_PRODUCT_TO_CART = "Successfully added product to cart.";
    public static final String SUCCESSFULLY_REMOVED_PRODUCT_FROM_CART = "Successfully removed product from cart.";

    // Cashier
    public static final String SUCCESSFULLY_ADDED_CASHIER = "Successfully added cashier - %s to shop - %s.";
    public static final String SUCCESSFULLY_REMOVED_CASHIER = "Successfully removed cashier with Id=%d from shop - %s.";

    // Checkout
    public static final String SUCCESSFULLY_ADDED_CHECKOUT = "Successfully added checkout to shop - %s.";
    public static final String SUCCESSFULLY_REMOVED_CHECKOUT = "Successfully removed checkout with Id=%d from shop - %s.";

    // Product
    public static final String SUCCESSFULLY_ADDED_PRODUCT = "Successfully added product %s to shop - %s.";
    public static final String SUCCESSFULLY_REMOVED_PRODUCT = "Successfully removed product with Id=%d from shop - %s.";

    // Receipt
    public static final String SUCCESSFULLY_MADE_PAYMENT = "Client with Id=%d have successfully made payment!\n%s";

    public static final String[] AVAILABLE_COMMANDS = {
            "RegisterShop {shopName}",
            "RemoveShop {shopId}",
            "AddProductToShop {shopId} {FoodProduct/NonFoodProduct} {name} {quantity} {deliveryPrice} {expirationDate} {markupPercentage} {approachingExpirationDays} {discount}",
            "RemoveProductFromShop {shopId} {productId}",
            "AddCashierToShop {shopId} {name} {monthlySalary}",
            "RemoveCashierFromShop {shopId} {cashierId}",
            "AddCheckoutToShop {shopId}",
            "RemoveCheckoutFromShop {shopId} {checkoutId}",
            "RegisterClient {name} {budget}",
            "RemoveClient {clientId}",
            "GetClient {clientId}",
            "AddProductToCart {clientId} {shopId} {productId} {desiredQuantity}",
            "RemoveProductFromCart {clientId} {shopId} {productId}",
            "ProcessCheckout {clientId} {shopId}",
            "GetReceiptInformation {serialNumber}",
            "GetShopInformation {shopId}",
            "GetAllShops",
            "Stop"
    };
}
