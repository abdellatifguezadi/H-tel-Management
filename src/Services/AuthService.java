package Services;

import Model.Client;
import repository.ClientRepository;

public class AuthService {

    private final ClientRepository clientRepository;
    private Client currentUser;

    public AuthService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public boolean register(String fullName, String email, String password) {
        if (isInvalidEmail(email)) {
            throw new IllegalArgumentException("email invalide");
        }
        if (isInvalidPassword(password)) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 6 caractères");
        }
        if (isInvalidFullName(fullName)) {
            throw new IllegalArgumentException("Le nom complet doit contenir au moins 3 caractères");
        }
        if (clientRepository.existsEmail(email)) {
            throw new IllegalArgumentException("Un compte avec cet email existe déjà");
        }

        boolean isFirstUser = clientRepository.findAll().isEmpty();

        Client.Role role = isFirstUser ? Client.Role.ADMIN : Client.Role.CLIENT;

        Client client = new Client(fullName.trim(), email.toLowerCase(), password, role);
        clientRepository.save(client);
        return true;
    }

    public boolean login(String email, String password) {
        return clientRepository.findByEmail(email.toLowerCase())
                .filter(client -> client.getPassword().equals(password))
                .map(client -> {
                    currentUser = client;
                    return true;
                }).orElse(false);
    }

    public void logout() {
        currentUser = null;
    }

    public Client getCurrentUser() {
        return currentUser;
    }

    public boolean isConnect() {
        return currentUser != null;
    }


    public boolean updateProfile(String fullName, String email) {
        if (currentUser == null) {
            throw new IllegalStateException("Aucun utilisateur connecté");
        }

        if (isInvalidEmail(email)) {
            throw new IllegalArgumentException("Email invalide");
        }

        if (isInvalidFullName(fullName)) {
            throw new IllegalArgumentException("Le nom complet ne peut pas être vide et au moins 3 caractères");
        }

        if (!email.equalsIgnoreCase(currentUser.getEmail()) && clientRepository.existsEmail(email)) {
            throw new IllegalArgumentException("Un compte avec cet email existe déjà");
        }


        currentUser.setFullName(fullName.trim());
        currentUser.setEmail(email.toLowerCase());
        clientRepository.save(currentUser);

        return true;
    }


    public boolean changePassword(String oldPassword, String newPassword) {
        if (currentUser == null) {
            throw new IllegalStateException("Aucun utilisateur connecté");
        }

        if (!currentUser.getPassword().equals(oldPassword)) {
            throw new IllegalArgumentException("Ancien mot de passe incorrect");
        }

        if (isInvalidPassword(newPassword)) {
            throw new IllegalArgumentException("Le nouveau mot de passe doit contenir au moins 6 caractères");
        }

        currentUser.setPassword(newPassword);
        clientRepository.save(currentUser);
        return true;
    }


    public boolean isInvalidEmail(String email) {
        return email == null
            || !email.contains("@")
            || !email.contains(".")
            || email.trim().length() < 5;
    }


    public boolean isInvalidPassword(String password) {
        return password.length() < 6;
    }


    public boolean isInvalidFullName(String fullName) {
        return  fullName.trim().length() < 3;
    }



}
