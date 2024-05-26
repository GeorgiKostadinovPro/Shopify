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

    String addClientToShop(String[] args);
    String removeClientFromShop(String[] args);

    String addProductsToCart(String[] args);
    String removeProductsFromCart(String[] args);

    String processCheckout(String[] args);
    String getShopInformation(String[] args);
    String getAllShops();
}
