package core.contracts;

public interface Controller {
    String registerShop(String[] args);
    String removeShop(String[] args);

    String addProductToShop(String[] args);
    String removeProductFromShop(String[] args);

    String addCashierToShop(String[] args);
    String removeCashierFromShop(String[] args);

    String addCheckoutToShop(String[] args);
    String removeCheckoutFromShop(String[] args);

    String registerClient(String[] args);
    String removeClient(String[] args);
    String getClient(String[] args);

    String addProductToCart(String[] args);
    String removeProductFromCart(String[] args);

    String processCheckout(String[] args);

    String getReceiptInformation(String[] args);
    String getShopInformation(String[] args);
    String getAllShops();
}
