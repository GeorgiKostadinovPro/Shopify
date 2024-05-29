package repositories;

import models.contracts.Cashier;
import repositories.contracts.Repository;

import java.util.*;

public class CashierRepository implements Repository<Cashier> {
    private final Map<Integer, Cashier> cashiers;

    public CashierRepository() {
        this.cashiers = new HashMap<>();
    }

    @Override
    public void add(Cashier entity) {
        this.cashiers.put(entity.getId(), entity);
    }

    @Override
    public void remove(int id) {
        this.cashiers.remove(id);
    }

    @Override
    public Cashier getById(int id) {
        return this.cashiers.get(id);
    }

    @Override
    public Collection<Cashier> getAll() {
        return Collections.unmodifiableCollection(this.cashiers.values());
    }
}
