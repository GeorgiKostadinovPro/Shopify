package repositories;

import models.clients.contracts.Client;
import repositories.contracts.Repository;

import java.util.*;

public class ClientRepository implements Repository<Client> {
    private final Map<Integer, Client> clients;

    public ClientRepository() {
        this.clients = new HashMap<>();
    }

    @Override
    public void add(Client entity) {
        this.clients.put(entity.getId(), entity);
    }

    @Override
    public void remove(int id) {
        this.clients.remove(id);
    }

    @Override
    public Client getById(int id) {
        return this.clients.get(id);
    }

    @Override
    public Collection<Client> getAll() {
        return Collections.unmodifiableCollection(this.clients.values());
    }
}
