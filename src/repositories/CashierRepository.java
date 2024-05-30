package repositories;

import models.contracts.ICashier;
import repositories.contracts.Repository;

import java.util.*;

public class CashierRepository implements Repository<ICashier> {
    private final Map<Integer, ICashier> cashiers;

    public CashierRepository() {
        this.cashiers = new HashMap<>();
    }

    @Override
    public void add(ICashier entity) {
        this.cashiers.put(entity.getId(), entity);
    }

    @Override
    public void remove(int id) {
        this.cashiers.remove(id);
    }

    @Override
    public ICashier getById(int id) {
        return this.cashiers.get(id);
    }

    @Override
    public Collection<ICashier> getAll() {
        return Collections.unmodifiableCollection(this.cashiers.values());
    }
}
