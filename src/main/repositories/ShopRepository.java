package repositories;

import models.contracts.IShop;
import repositories.contracts.Repository;

import java.util.*;

public class ShopRepository implements Repository<IShop> {
    private final Map<Integer, IShop> shops;

    public ShopRepository() {
        this.shops = new HashMap<>();
    }

    @Override
    public void add(IShop entity) {
        this.shops.put(entity.getId(), entity);
    }

    @Override
    public void remove(int id) {
        this.shops.remove(id);
    }

    @Override
    public IShop getById(int id) {
        return this.shops.get(id);
    }

    @Override
    public Collection<IShop> getAll() {
        return Collections.unmodifiableCollection(this.shops.values());
    }
}
