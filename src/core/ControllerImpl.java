package core;

import common.exceptions.ShopNotExistException;
import common.messages.ExceptionMessages;
import common.messages.OutputMessages;
import core.contracts.Controller;
import models.shop.contracts.Shop;
import repositories.ShopRepository;

import java.math.BigDecimal;

public class ControllerImpl implements Controller {
    private final ShopRepository shopRepository;

    public ControllerImpl() {
        this.shopRepository = new ShopRepository();
    }

    @Override
    public String registerShop(String[] args) {
        String shopName = args[0];
        int shopId = this.shopRepository.getAll().size() + 1;

        Shop shop = new models.shop.Shop(shopId, shopName);
        this.shopRepository.add(shop);

        return String.format(OutputMessages.SUCCESSFULLY_REGISTERED_SHOP, shopName);
    }

    @Override
    public String removeShop(String[] args) {
        Shop shop = this.shopRepository.getById(Integer.parseInt(args[0]));

        if (shop == null) {
            throw new ShopNotExistException(ExceptionMessages.INVALID_SHOP_ID);
        }

        this.shopRepository.remove(shop.getId());

        return String.format(OutputMessages.SUCCESSFULLY_REMOVED_SHOP, shop.getShortInfo());
    }

    @Override
    public String addProductToShop(String[] args) {
        return "";
    }

    @Override
    public String removeProductFromShop(String[] args) {
        return "";
    }

    @Override
    public String addCashierToShop(String[] args) {
        int shopId = Integer.parseInt(args[0]);
        String name = args[1];
        BigDecimal monthlySalary = new BigDecimal(args[2]);

        Shop shop = this.shopRepository.getById(shopId);

        if (shop == null) {
            throw new ShopNotExistException(ExceptionMessages.INVALID_SHOP_ID);
        }

        shop.addCashier(name, monthlySalary);

        return String.format(OutputMessages.SUCCESSFULLY_ADDED_CASHIER, name, shop.getShortInfo());
    }

    @Override
    public String removeCashierFromShop(String[] args) {
        int shopId = Integer.parseInt(args[0]);
        int cashierId = Integer.parseInt(args[1]);

        Shop shop = this.shopRepository.getById(shopId);

        if (shop == null) {
            throw new ShopNotExistException(ExceptionMessages.INVALID_SHOP_ID);
        }

        shop.removeCashier(cashierId);

        return String.format(OutputMessages.SUCCESSFULLY_REMOVED_CASHIER, cashierId, shop.getShortInfo());
    }

    @Override
    public String addCheckoutToShop(String[] args) {
        return "";
    }

    @Override
    public String removeCheckoutFromShop(String[] args) {
        return "";
    }

    @Override
    public String addClientToShop(String[] args) {
        int shopId = Integer.parseInt(args[0]);
        String name = args[1];
        BigDecimal budget = new BigDecimal(args[2]);

        Shop shop = this.shopRepository.getById(shopId);

        if (shop == null) {
            throw new ShopNotExistException(ExceptionMessages.INVALID_SHOP_ID);
        }

        shop.addClient(name, budget);

        return String.format(OutputMessages.SUCCESSFULLY_ADDED_CLIENT, name, shop.getShortInfo());
    }

    @Override
    public String removeClientFromShop(String[] args) {
        int shopId = Integer.parseInt(args[0]);
        int clientId = Integer.parseInt(args[1]);

        Shop shop = this.shopRepository.getById(shopId);

        if (shop == null) {
            throw new ShopNotExistException(ExceptionMessages.INVALID_SHOP_ID);
        }

        shop.removeClient(clientId);

        return String.format(OutputMessages.SUCCESSFULLY_REMOVED_CLIENT, clientId, shop.getShortInfo());
    }

    @Override
    public String addProductToCart(String[] args) {
        return "";
    }

    @Override
    public String removeProductFromCart(String[] args) {
        return "";
    }

    @Override
    public String processCheckout(String[] args) {
        return "";
    }

    @Override
    public String getShopInformation(String[] args) {
        int shopId = Integer.parseInt(args[0]);

        Shop shop = this.shopRepository.getById(shopId);

        if (shop == null) {
            throw new ShopNotExistException(ExceptionMessages.INVALID_SHOP_ID);
        }

        return shop.toString();
    }

    @Override
    public String getAllShops() {
        if (this.shopRepository.getAll().isEmpty()) {
            return OutputMessages.NO_SHOP_REGISTERED;
        }

        StringBuilder sb = new StringBuilder();

        this.shopRepository.getAll().forEach(s -> sb.append(s.getShortInfo()).append("\n"));

        return sb.toString().trim();
    }
}
