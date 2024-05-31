package core;

import common.exceptions.ClientNotExistException;
import common.exceptions.ProductNotExistException;
import common.exceptions.ShopNotExistException;
import common.messages.ExceptionMessages;
import common.messages.OutputMessages;
import core.contracts.Controller;
import models.Client;
import models.Shop;
import models.contracts.IClient;
import models.Receipt;
import models.contracts.IProduct;
import models.contracts.IShop;
import repositories.ClientRepository;
import repositories.ShopRepository;
import utilities.FileService;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ShopifyController implements Controller {
    private final FileService fileService;
    private final ShopRepository shopRepository;
    private final ClientRepository clientRepository;

    public ShopifyController() {
        this.fileService = new FileService();
        this.shopRepository = new ShopRepository();
        this.clientRepository = new ClientRepository();
    }

    @Override
    public String registerShop(String[] args) {
        String shopName = args[0];
        int shopId = this.shopRepository.getAll().size() + 1;

        IShop shop = new Shop(shopId, shopName);
        this.shopRepository.add(shop);

        return String.format(OutputMessages.SUCCESSFULLY_REGISTERED_SHOP, shopName);
    }

    @Override
    public String removeShop(String[] args) {
        IShop shop = this.shopRepository.getById(Integer.parseInt(args[0]));

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

        IShop shop = this.shopRepository.getById(shopId);

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

        IShop shop = this.shopRepository.getById(shopId);

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

        IShop shop = this.shopRepository.getById(shopId);

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

        IShop shop = this.shopRepository.getById(shopId);

        if (shop == null) {
            throw new ShopNotExistException(ExceptionMessages.INVALID_SHOP_ID);
        }

        shop.removeCashier(cashierId);

        return String.format(OutputMessages.SUCCESSFULLY_REMOVED_CASHIER, cashierId, shop.getShortInfo());
    }

    @Override
    public String addCheckoutToShop(String[] args) {
        int shopId = Integer.parseInt(args[0]);

        IShop shop = this.shopRepository.getById(shopId);

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

        IShop shop = this.shopRepository.getById(shopId);

        if (shop == null) {
            throw new ShopNotExistException(ExceptionMessages.INVALID_SHOP_ID);
        }

        shop.removeCheckout(checkoutId);

        return String.format(OutputMessages.SUCCESSFULLY_REMOVED_CHECKOUT, checkoutId, shop.getShortInfo());
    }

    @Override
    public String registerClient(String[] args) {
        String name = args[0];
        BigDecimal budget = new BigDecimal(args[1]);

        int clientId = this.clientRepository.getAll().size() + 1;

        IClient client = new Client(clientId, name, budget);
        this.clientRepository.add(client);

        return String.format(OutputMessages.SUCCESSFULLY_REGISTERED_CLIENT, client);
    }

    @Override
    public String removeClient(String[] args) {
        int clientId = Integer.parseInt(args[0]);

        if (this.clientRepository.getById(clientId) == null) {
            throw new ClientNotExistException(ExceptionMessages.CLIENT_NOT_PRESENTED);
        }

        this.clientRepository.remove(clientId);

        return String.format(OutputMessages.SUCCESSFULLY_REMOVED_CLIENT, clientId);
    }

    @Override
    public String getClient(String[] args) {
        int clientId = Integer.parseInt(args[0]);

        IClient client = this.clientRepository.getById(clientId);

        if (client == null) {
            throw new ClientNotExistException(ExceptionMessages.CLIENT_NOT_PRESENTED);
        }

        return client.toString();
    }

    @Override
    public String addProductToCart(String[] args) {
        int clientId = Integer.parseInt(args[0]);
        int shopId = Integer.parseInt(args[1]);
        int productId = Integer.parseInt(args[2]);
        int desiredQuantity = Integer.parseInt(args[3]);

        IClient client = this.clientRepository.getById(clientId);

        if (client == null) {
            throw new ClientNotExistException(ExceptionMessages.CLIENT_NOT_PRESENTED);
        }

        IShop shop = this.shopRepository.getById(shopId);

        if (shop == null) {
            throw new ShopNotExistException(ExceptionMessages.INVALID_SHOP_ID);
        }

        IProduct product = shop.getProducts()
                .stream()
                .filter(p -> p.getId() == productId)
                .findFirst()
                .orElse(null);

        if (product == null) {
            throw new ProductNotExistException(ExceptionMessages.INVALID_PRODUCT_ID);
        }

        client.getCart().addProduct(product, desiredQuantity);

        return OutputMessages.SUCCESSFULLY_ADDED_PRODUCT_TO_CART;
    }

    @Override
    public String removeProductFromCart(String[] args) {
        int clientId = Integer.parseInt(args[0]);
        int shopId = Integer.parseInt(args[1]);
        int productId = Integer.parseInt(args[2]);
        int desiredQuantity = Integer.parseInt(args[3]);

        IClient client = this.clientRepository.getById(clientId);

        if (client == null) {
            throw new ClientNotExistException(ExceptionMessages.CLIENT_NOT_PRESENTED);
        }

        IShop shop = this.shopRepository.getById(shopId);

        if (shop == null) {
            throw new ShopNotExistException(ExceptionMessages.INVALID_SHOP_ID);
        }

        IProduct product = shop.getProducts()
                .stream()
                .filter(p -> p.getId() == productId)
                .findFirst()
                .orElse(null);

        if (product == null) {
            throw new ProductNotExistException(ExceptionMessages.INVALID_PRODUCT_ID);
        }

        client.getCart().removeProduct(product);

        return OutputMessages.SUCCESSFULLY_REMOVED_PRODUCT_FROM_CART;
    }

    @Override
    public String processCheckout(String[] args) {
        int clientId = Integer.parseInt(args[0]);
        int shopId = Integer.parseInt(args[1]);

        IClient client = this.clientRepository.getById(clientId);

        if (client == null) {
            throw new ClientNotExistException(ExceptionMessages.CLIENT_NOT_PRESENTED);
        }

        IShop shop = this.shopRepository.getById(shopId);

        if (shop == null) {
            throw new ShopNotExistException(ExceptionMessages.INVALID_SHOP_ID);
        }

        Receipt receipt = shop.processCheckout(client);

        this.fileService.saveReceiptAsFile(receipt);

        return String.format(OutputMessages.SUCCESSFULLY_MADE_PAYMENT, clientId, receipt);
    }

    @Override
    public String getReceiptInformation(String[] args) {
        String serialNumber = args[0];

        return this.fileService.readReceiptFromFile(serialNumber);
    }

    @Override
    public String getShopInformation(String[] args) {
        int shopId = Integer.parseInt(args[0]);

        IShop shop = this.shopRepository.getById(shopId);

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
