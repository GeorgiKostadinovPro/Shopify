package repositories;

import models.contracts.IClient;
import repositories.contracts.Repository;

import java.util.*;

public class ClientRepository implements Repository<IClient> {
    private final Map<Integer, IClient> clients;

    public ClientRepository() {
        this.clients = new HashMap<>();
    }

    @Override
    public void add(IClient entity) {
        this.clients.put(entity.getId(), entity);
    }

    @Override
    public void remove(int id) {
        this.clients.remove(id);
    }

    @Override
    public IClient getById(int id) {
        return this.clients.get(id);
    }

    @Override
    public Collection<IClient> getAll() {
        return Collections.unmodifiableCollection(this.clients.values());
    }
}
