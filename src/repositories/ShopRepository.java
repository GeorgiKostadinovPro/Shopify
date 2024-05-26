package repositories;

import models.shop.contracts.Shop;
import repositories.contracts.Repository;

import java.util.*;

public class ShopRepository implements Repository<Shop> {
    private final Map<Integer, Shop> shops;

    public ShopRepository() {
        this.shops = new HashMap<>();
    }

    @Override
    public void add(Shop entity) {
        this.shops.put(entity.getId(), entity);
    }

    @Override
    public void remove(int id) {
        this.shops.remove(id);
    }

    @Override
    public Shop getById(int id) {
        return this.shops.get(id);
    }

    @Override
    public Collection<Shop> getAll() {
        return Collections.unmodifiableCollection(this.shops.values());
    }
}
