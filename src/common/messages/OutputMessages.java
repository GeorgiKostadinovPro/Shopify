package common.messages;

public class OutputMessages {
    public static final String SUCCESSFULLY_REGISTERED_SHOP = "Successfully registered %s in the system";
    public static final String NO_SHOP_REGISTERED = "There are NOT any shop registered!";

    public static final String[] AVAILABLE_COMMANDS = {
            "RegisterShop {shopName}",
            "RemoveShop {shopId}",
            "AddProductToShop {shopId} {FoodProduct/NonFoodProduct} {name} {quantity} {deliveryPrice} {expirationDate} {markupPercentage} {approachingExpirationDays} {discount}",
            "RemoveProductFromShop {shopId} {productId}",
            "AddCashierToShop {shopId} {name} {monthlySalary}",
            "RemoveCashierFromShop {shopId} {cashierId}",
            "AddCheckoutToShop {shopId}",
            "RemoveCheckoutFromShop {shopId} {checkoutId}",
            "AddClientToShop {shopId} {clientId}",
            "RemoveClientFromShop {shopId} {name} {budget}",
            "AddProductToCart {clientId} {productId} {desiredQuantity}",
            "RemoveProductFromCart {clientId} {productId}",
            "ProcessCheckout {clientId}",
            "GetShopInformation {shopId}",
            "GetAllShops",
            "Stop"
    };
}
