package repository;

import Model.Client;

import java.util.*;

public class MemoryClient implements ClientRepository{

    private final Map<UUID,Client> clients = new HashMap<>();

    private final Map<String , UUID> emailToId = new HashMap<>();


    @Override
    public Client save(Client client) {
        clients.put(client.getId(), client);
        emailToId.put(client.getEmail(), client.getId());
        return client;
    }

    @Override
    public List<Client> findAll() {
        return new ArrayList<>(clients.values());
    }

    @Override
    public Optional<Client> findById(UUID id) {
        return Optional.ofNullable(clients.get(id));
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        UUID id = emailToId.get(email) ;
        return id != null ? Optional.ofNullable(clients.get(id)) : Optional.empty() ;
    }

    @Override
    public void delete(UUID id) {
        Client client = clients.remove(id);
        if (client != null) {
            emailToId.remove(client.getEmail());
        }
    }

    @Override
    public boolean existsEmail(String email) {
        return emailToId.containsKey(email);
    }
}
