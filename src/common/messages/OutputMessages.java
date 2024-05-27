package common.messages;

public class OutputMessages {
    // Shop
    public static final String SUCCESSFULLY_REGISTERED_SHOP = "Successfully registered %s in the system.";
    public static final String NO_SHOP_REGISTERED = "There are NOT any shop registered!";
    public static final String SUCCESSFULLY_REMOVED_SHOP = "Successfully removed %s from the system.";

    // Client
    public static final String SUCCESSFULLY_REMOVED_CLIENT = "Successfully removed client with Id=%d from %s.";

    public static final String[] AVAILABLE_COMMANDS = {
            "RegisterShop {shopName}",
            "RemoveShop {shopId}",
            "AddProductToShop {shopId} {FoodProduct/NonFoodProduct} {name} {quantity} {deliveryPrice} {expirationDate} {markupPercentage} {approachingExpirationDays} {discount}",
            "RemoveProductFromShop {shopId} {productId}",
            "AddCashierToShop {shopId} {name} {monthlySalary}",
            "RemoveCashierFromShop {shopId} {cashierId}",
            "AddCheckoutToShop {shopId}",
            "RemoveCheckoutFromShop {shopId} {checkoutId}",
            "AddClientToShop {shopId} {name} {budget}",
            "RemoveClientFromShop {shopId} {clientId}",
            "AddProductToCart {shopId} {clientId} {productId} {desiredQuantity}",
            "RemoveProductFromCart {shopId} {clientId} {productId}",
            "ProcessCheckout {clientId}",
            "GetShopInformation {shopId}",
            "GetAllShops",
            "Stop"
    };
}
