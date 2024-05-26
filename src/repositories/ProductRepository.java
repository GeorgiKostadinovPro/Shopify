package repositories;

import models.products.contracts.Product;
import repositories.contracts.Repository;

import java.util.*;

public class ProductRepository implements Repository<Product> {
    private final Map<Integer, Product> products;

    public ProductRepository() {
        this.products = new HashMap<>();
    }

    @Override
    public void add(Product entity) {
        this.products.put(entity.getId(), entity);
    }

    @Override
    public void remove(int id) {
        this.products.remove(id);
    }

    @Override
    public Product getById(int id) {
        return this.products.get(id);
    }

    @Override
    public Collection<Product> getAll() {
        return Collections.unmodifiableCollection(this.products.values());
    }
}
