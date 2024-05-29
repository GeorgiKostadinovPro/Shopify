package repositories;

import models.contracts.Checkout;
import repositories.contracts.Repository;

import java.util.*;

public class CheckoutRepository implements Repository<Checkout> {
    private final Map<Integer, Checkout> checkouts;

    public CheckoutRepository() {
        this.checkouts = new HashMap<>();
    }

    @Override
    public void add(Checkout entity) {
        this.checkouts.put(entity.getId(), entity);
    }

    @Override
    public void remove(int id) {
        this.checkouts.remove(id);
    }

    @Override
    public Checkout getById(int id) {
        return this.checkouts.get(id);
    }

    @Override
    public Collection<Checkout> getAll() {
        return Collections.unmodifiableCollection(this.checkouts.values());
    }
}
