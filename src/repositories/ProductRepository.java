package repositories;

import models.contracts.IProduct;
import repositories.contracts.Repository;

import java.util.*;

public class ProductRepository implements Repository<IProduct> {
    private final Map<Integer, IProduct> products;

    public ProductRepository() {
        this.products = new HashMap<>();
    }

    @Override
    public void add(IProduct entity) {
        this.products.put(entity.getId(), entity);
    }

    @Override
    public void remove(int id) {
        this.products.remove(id);
    }

    @Override
    public IProduct getById(int id) {
        return this.products.get(id);
    }

    @Override
    public Collection<IProduct> getAll() {
        return Collections.unmodifiableCollection(this.products.values());
    }
}
