package core;

import common.exceptions.ShopNotExistException;
import common.messages.ExceptionMessages;
import common.messages.OutputMessages;
import core.contracts.Controller;
import models.contracts.Shop;
import repositories.ShopRepository;
import utilities.FileService;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ControllerImpl implements Controller {
    private final ShopRepository shopRepository;

    public ControllerImpl() {
        this.shopRepository = new ShopRepository();
    }

    @Override
    public String registerShop(String[] args) {
        String shopName = args[0];
        int shopId = this.shopRepository.getAll().size() + 1;

        Shop shop = new models.Shop(shopId, shopName);
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
        int shopId = Integer.parseInt(args[0]);
        String type = args[1];
        String name = args[2];
        int quantity = Integer.parseInt(args[3]);
        BigDecimal deliveryPrice = new BigDecimal(args[4]);
        LocalDate expirationDate = LocalDate.parse(args[5]);
        BigDecimal markupPercentage = new BigDecimal(args[6]);
        int approachingExpirationDays = Integer.parseInt(args[7]);
        BigDecimal approachingExpirationDiscount= new BigDecimal(args[8]);

        Shop shop = this.shopRepository.getById(shopId);

        if (shop == null) {
            throw new ShopNotExistException(ExceptionMessages.INVALID_SHOP_ID);
        }

        shop.addProduct(
                type,
                name,
                quantity,
                deliveryPrice,
                expirationDate,
                markupPercentage,
                approachingExpirationDays,
                approachingExpirationDiscount);

        return String.format(OutputMessages.SUCCESSFULLY_ADDED_PRODUCT, name, shop.getShortInfo());
    }

    @Override
    public String removeProductFromShop(String[] args) {
        int shopId = Integer.parseInt(args[0]);
        int productId = Integer.parseInt(args[1]);

        Shop shop = this.shopRepository.getById(shopId);

        if (shop == null) {
            throw new ShopNotExistException(ExceptionMessages.INVALID_SHOP_ID);
        }

        shop.removeProduct(productId);

        return String.format(OutputMessages.SUCCESSFULLY_REMOVED_PRODUCT, productId, shop.getShortInfo());
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
        int shopId = Integer.parseInt(args[0]);

        Shop shop = this.shopRepository.getById(shopId);

        if (shop == null) {
            throw new ShopNotExistException(ExceptionMessages.INVALID_SHOP_ID);
        }

        shop.addCheckout();

        return String.format(OutputMessages.SUCCESSFULLY_ADDED_CHECKOUT, shop.getShortInfo());
    }

    @Override
    public String removeCheckoutFromShop(String[] args) {
        int shopId = Integer.parseInt(args[0]);
        int checkoutId = Integer.parseInt(args[1]);

        Shop shop = this.shopRepository.getById(shopId);

        if (shop == null) {
            throw new ShopNotExistException(ExceptionMessages.INVALID_SHOP_ID);
        }

        shop.removeCheckout(checkoutId);

        return String.format(OutputMessages.SUCCESSFULLY_REMOVED_CHECKOUT, checkoutId, shop.getShortInfo());
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
    public String getReceiptInformation(String[] args) {
        String serialNumber = args[0];

        FileService service = new FileService();

        return service.readReceiptFromFile(serialNumber);
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
