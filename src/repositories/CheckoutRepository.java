package repositories;

import models.contracts.ICheckout;
import repositories.contracts.Repository;

import java.util.*;

public class CheckoutRepository implements Repository<ICheckout> {
    private final Map<Integer, ICheckout> checkouts;

    public CheckoutRepository() {
        this.checkouts = new HashMap<>();
    }

    @Override
    public void add(ICheckout entity) {
        this.checkouts.put(entity.getId(), entity);
    }

    @Override
    public void remove(int id) {
        this.checkouts.remove(id);
    }

    @Override
    public ICheckout getById(int id) {
        return this.checkouts.get(id);
    }

    @Override
    public Collection<ICheckout> getAll() {
        return Collections.unmodifiableCollection(this.checkouts.values());
    }
}
