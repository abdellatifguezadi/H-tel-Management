package UI;

import Model.Client;
import Model.Hotel;
import Model.Reservation;
import Services.AuthService;
import Services.HotelService;
import Services.ReservationService;
import Utils.ConsoleUtils;
import Utils.DisplayUtils;
import repository.MemoryClient;
import repository.MemoryHotel;
import repository.MemoryReservation;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class HotelManagementUI {
    private final AuthService authService;
    private final HotelService hotelService;
    private final ReservationService reservationService;
    private boolean running = true;

    public HotelManagementUI() {
        MemoryClient clientRepository = new MemoryClient();
        MemoryHotel hotelRepository = new MemoryHotel();
        MemoryReservation reservationRepository = new MemoryReservation();

        this.reservationService = new ReservationService(reservationRepository, hotelRepository, clientRepository);
        this.hotelService = new HotelService(hotelRepository, reservationService);
        this.authService = new AuthService(clientRepository);

        initializeSampleData();
    }

    private void initializeSampleData() {

            hotelService.createHotel("H1", "Hotel Nador", "Nador rue taouima", 50, 3.5);
            hotelService.createHotel("H2", "Hotel Marrakech", "Avenue hassan 2", 30, 4.2);
            hotelService.createHotel("H3", "Hotel Casablanca", "Boulevard d'anfa, Casablanca", 25, 4.8);
            hotelService.createHotel("H4", "Hotel Fez", "Boulevard Fez, Fez", 22, 4.6);
    }

    public void start() {
        DisplayUtils.displayHeader("SYSTÈME DE GESTION HÔTELIÈRE");
        DisplayUtils.displayInfo("Bienvenue dans notre système de réservation d'hôtels!");

        while (running) {
            try {
                if (authService.isConnect()) {
                    showAuthenticatedMenu();
                } else {
                    showPublicMenu();
                }
            } catch (Exception e) {
                DisplayUtils.displayError("Une erreur inattendue s'est produite: " + e.getMessage());
            }
        }

        DisplayUtils.displayInfo("Merci d'avoir utilisé notre système. Au revoir!");
    }

    private void showPublicMenu() {
        DisplayUtils.displayMenu("MENU PRINCIPAL",
                "S'inscrire",
                "Se connecter",
                "Quitter");

        int choice = ConsoleUtils.readMenuChoice( 1, 3);

        switch (choice) {
            case 1 : handleRegister();
                    break;
            case 2 : handleLogin();
                    break;
            case 3 : running = false;
        }
    }

    private void showAuthenticatedMenu() {
        Client currentUser = authService.getCurrentUser();
        DisplayUtils.displayHeader("CONNECTÉ COMME: " + currentUser.getFullName() +
                                 " (" + (currentUser.isAdmin() ? "ADMIN" : "CLIENT") + ")");

        if (currentUser.isAdmin()) {
            DisplayUtils.displayMenu("MENU ADMINISTRATEUR",
                    "Créer un hôtel",
                    "Modifier un hôtel",
                    "Supprimer un hôtel",
                    "Lister tous les hôtels",
                    "Voir toutes les réservations",
                    "Annuler une réservation",
                    "Mon historique de réservations",
                    "Modifier mon profil",
                    "Changer mot de passe",
                    "Se déconnecter");

            int choice = ConsoleUtils.readMenuChoice( 1, 10);

            switch (choice) {
                case 1: handleCreateHotel();
                        break;
                case 2: handleUpdateHotel();
                            break;
                case 3 : handleDeleteHotel();
                        break;
                case 4 : handleListAllHotels();
                        break;
                case 5 : handleViewAllReservations();
                        break;
                case 6 : handleCancelReservation();
                        break;
                case 7 : handleViewMyReservations();
                        break;
                case 8 : handleUpdateProfile();
                        break;
                case 9 : handleChangePassword();
                        break;
                case 10 : handleLogout();
                        break;
            }
        } else {
            DisplayUtils.displayMenu("MENU CLIENT",
                    "Lister les hôtels disponibles",
                    "Réserver une chambre",
                    "Annuler une réservation",
                    "Mon historique de réservations",
                    "Modifier mon profil",
                    "Changer mot de passe",
                    "Se déconnecter");

            int choice = ConsoleUtils.readMenuChoice( 1, 7);

            switch (choice) {
                case 1 : handleListAvailableHotels();
                        break;
                case 2 : handleReserveRoom();
                        break;
                case 3 : handleCancelReservation();
                        break;
                case 4 : handleViewMyReservations();
                        break;
                case 5 : handleUpdateProfile();
                        break;
                case 6 : handleChangePassword();
                        break;
                case 7 : handleLogout();
                        break;
            }
        }
    }

    private void handleRegister() {
        DisplayUtils.displayHeader("INSCRIPTION");

        try {
            String fullName = ConsoleUtils.readName("Nom complet: ");
            String email = ConsoleUtils.readEmail("Email: ");
            String password = ConsoleUtils.readPassword("Mot de passe (min 6 caractères): ");

            boolean success = authService.register(fullName, email, password);
            if (success) {
                DisplayUtils.displaySuccess("Inscription réussie!");
                if (authService.getCurrentUser() != null && authService.getCurrentUser().isAdmin()) {
                    DisplayUtils.displayInfo("Vous êtes le premier utilisateur, vous avez les privilèges administrateur.");
                }
            }
        } catch (Exception e) {
            DisplayUtils.displayError(e.getMessage());
        }
    }

    private void handleLogin() {
        DisplayUtils.displayHeader("CONNEXION");

        try {
            String email = ConsoleUtils.readEmail("Email: ");
            String password = ConsoleUtils.readPassword("Mot de passe: ");

            boolean success = authService.login(email, password);
            if (success) {
                DisplayUtils.displaySuccess("Connexion réussie! Bienvenue " +
                                          authService.getCurrentUser().getFullName());
            } else {
                DisplayUtils.displayError("Email ou mot de passe incorrect.");
            }
        } catch (Exception e) {
            DisplayUtils.displayError(e.getMessage());
        }
    }

    private void handleCreateHotel() {
        DisplayUtils.displayHeader("CRÉER UN HÔTEL");

        try {
            String hotelId = ConsoleUtils.readNonEmptyString("ID de l'hôtel: ");
            String name = ConsoleUtils.readNonEmptyString("Nom de l'hôtel: ");
            String address = ConsoleUtils.readNonEmptyString("Adresse: ");
            int availableRooms = ConsoleUtils.readPositiveInt("Nombre de chambres disponibles: ");
            double rating = ConsoleUtils.readRating("Note (0-5): ");

            Hotel hotel = hotelService.createHotel(hotelId, name, address, availableRooms, rating);
            DisplayUtils.displaySuccess("Hôtel créé avec succès: " + hotel.getName());
        } catch (Exception e) {
            DisplayUtils.displayError(e.getMessage());
        }
    }

    private void handleUpdateHotel() {
        DisplayUtils.displayHeader("MODIFIER UN HÔTEL");

        try {
            handleListAllHotels();
            String hotelId = ConsoleUtils.readNonEmptyString("ID de l'hôtel à modifier: ");

            Optional<Hotel> hotelOptional = hotelService.getHotelById(hotelId);
            if (hotelOptional.isEmpty()) {
                DisplayUtils.displayError("Hôtel introuvable.");
                return;
            }

            Hotel hotel = hotelOptional.get();
            DisplayUtils.displayInfo("Hôtel actuel: " + hotel.getName());

            String name = ConsoleUtils.readNonEmptyString("Nouveau nom (" + hotel.getName() + "): ");
            String address = ConsoleUtils.readNonEmptyString("Nouvelle adresse (" + hotel.getAddress() + "): ");
            int availableRooms = ConsoleUtils.readNonNegativeInt("Nouveau nombre de chambres (" + hotel.getAvailableRooms() + "): ");
            double rating = ConsoleUtils.readRating("Nouvelle note (" + hotel.getRating() + "): ");

            boolean success = hotelService.updateHotel(hotelId, name, address, availableRooms, rating);
            if (success) {
                DisplayUtils.displaySuccess("Hôtel modifié avec succès!");
            }
        } catch (Exception e) {
            DisplayUtils.displayError(e.getMessage());
        }
    }

    private void handleDeleteHotel() {
        DisplayUtils.displayHeader("SUPPRIMER UN HÔTEL");

        try {
            handleListAllHotels();
            String hotelId = ConsoleUtils.readNonEmptyString("ID de l'hôtel à supprimer: ");

            Optional<Hotel> hotelOpt = hotelService.getHotelById(hotelId);
            if (hotelOpt.isEmpty()) {
                DisplayUtils.displayError("Hôtel introuvable.");
                return;
            }

            Hotel hotel = hotelOpt.get();
            DisplayUtils.displayInfo("Hôtel: " + hotel.getName());


                boolean success = hotelService.deleteHotel(hotelId);
                if (success) {
                    DisplayUtils.displaySuccess("Hôtel supprimé avec succès!");
                }

        } catch (Exception e) {
            DisplayUtils.displayError(e.getMessage());
        }
    }

    private void handleListAllHotels() {
        DisplayUtils.displayHeader("TOUS LES HÔTELS");

        List<Hotel> hotels = hotelService.getAllHotels();
        if (hotels.isEmpty()) {
            DisplayUtils.displayInfo("Aucun hôtel enregistré.");
            return;
        }

        DisplayUtils.displayTableHeader("ID", "Nom", "Adresse", "Chambres Dispo", "Note");
        for (Hotel hotel : hotels) {
            DisplayUtils.displayTableRow(
                    hotel.getHotelId(),
                    hotel.getName(),
                    hotel.getAddress(),
                    String.valueOf(hotel.getAvailableRooms()),
                    String.valueOf( hotel.getRating())
            );
        }
    }

    private void handleListAvailableHotels() {
        DisplayUtils.displayHeader("HÔTELS DISPONIBLES");

        List<Hotel> hotels = hotelService.getAvailableHotels();
        if (hotels.isEmpty()) {
            DisplayUtils.displayInfo("Aucun hôtel disponible actuellement.");
            return;
        }

        DisplayUtils.displayTableHeader("ID", "Nom", "Adresse", "Chambres Dispo", "Note");
        for (Hotel hotel : hotels) {
            DisplayUtils.displayTableRow(
                    hotel.getHotelId(),
                    hotel.getName(),
                    hotel.getAddress(),
                    String.valueOf(hotel.getAvailableRooms()),
                    String.valueOf( hotel.getRating())
            );
        }
    }

    private void handleReserveRoom() {
        DisplayUtils.displayHeader("RÉSERVER UNE CHAMBRE");

        try {
            handleListAvailableHotels();

            List<Hotel> availableHotels = hotelService.getAvailableHotels();
            if (availableHotels.isEmpty()) {
                DisplayUtils.displayError("Aucun hôtel disponible pour la réservation.");
                return;
            }

            String hotelId = ConsoleUtils.readNonEmptyString("ID de l'hôtel: ");
            int nights = ConsoleUtils.readPositiveInt("Nombre de nuits: ");

            Reservation reservation = reservationService.createReservation(
                    authService.getCurrentUser(), hotelId, nights);

            DisplayUtils.displaySuccess("Réservation créée avec succès!");
            DisplayUtils.displayInfo("ID de réservation: " + reservation.getId());
            DisplayUtils.displayInfo("Hôtel: " + hotelId);
            DisplayUtils.displayInfo("Nuits: " + nights);
        } catch (Exception e) {
            DisplayUtils.displayError(e.getMessage());
        }
    }

    private void handleCancelReservation() {
        DisplayUtils.displayHeader("ANNULER UNE RÉSERVATION");
        try {
            Client currentUser = authService.getCurrentUser();
            List<Reservation> reservations;
            if (currentUser.isAdmin()) {
                reservations = reservationService.getAllReservations();
                if (reservations.isEmpty()) {
                    DisplayUtils.displayInfo("Aucune réservation enregistrée.");
                    return;
                }
                DisplayUtils.displayInfo("Toutes les réservations:");
            } else {
                reservations = reservationService.getReservationsByClient(currentUser);
                if (reservations.isEmpty()) {
                    DisplayUtils.displayInfo("Vous n'avez aucune réservation.");
                    return;
                }
                DisplayUtils.displayInfo("Vos réservations:");
            }
            DisplayUtils.displayTableHeader("ID Réservation", "Client", "Hôtel", "Nuits", "Date");
            for (Reservation reservation : reservations) {
                String dateStr = String.valueOf(reservation.getTimestamp());
                String clientName = reservationService.getClientNameById(reservation.getClientId());
                DisplayUtils.displayTableRow(
                        reservation.getId().toString(),
                        clientName,
                        reservation.getHotelId(),
                        String.valueOf(reservation.getNights()),
                        dateStr
                );
            }
            UUID reservationId = ConsoleUtils.readUUID("ID de la réservation à annuler: ");
            boolean success = reservationService.cancelReservation(reservationId, currentUser);
            if (success){
                    DisplayUtils.displaySuccess("Réservation annulée avec succès!");
            }
        } catch (Exception e) {
            DisplayUtils.displayError(e.getMessage());
        }
    }

    private void handleViewMyReservations() {
        DisplayUtils.displayHeader("MON HISTORIQUE DE RÉSERVATIONS");

        try {
            List<Reservation> reservations = reservationService.getReservationsByClient(authService.getCurrentUser());
            if (reservations.isEmpty()) {
                DisplayUtils.displayInfo("Vous n'avez aucune réservation.");
                return;
            }

            DisplayUtils.displayTableHeader("ID", "Hôtel", "Nuits", "Date de réservation");

            reservations.sort((r1, r2) -> r2.getTimestamp().compareTo(r1.getTimestamp()));

            for (Reservation reservation : reservations) {
                String dateStr = String.valueOf(reservation.getTimestamp());
                DisplayUtils.displayTableRow(
                        String.valueOf(reservation.getId()),
                        reservation.getHotelId(),
                        String.valueOf(reservation.getNights()),
                        dateStr
                );
            }
        } catch (Exception e) {
            DisplayUtils.displayError(e.getMessage());
        }
    }

    private void handleViewAllReservations() {
        DisplayUtils.displayHeader("TOUTES LES RÉSERVATIONS");

        try {
            List<Reservation> reservations = reservationService.getAllReservations();
            if (reservations.isEmpty()) {
                DisplayUtils.displayInfo("Aucune réservation enregistrée.");
                return;
            }

            DisplayUtils.displayTableHeader("ID", "Client ID", "Hôtel", "Nuits", "Date");

            reservations.sort((r1, r2) -> r2.getTimestamp().compareTo(r1.getTimestamp()));

            for (Reservation reservation : reservations) {
                String dateStr = String.valueOf(reservation.getTimestamp());

                DisplayUtils.displayTableRow(
                        String.valueOf(reservation.getId()),
                        String.valueOf(reservation.getClientId()),
                        reservation.getHotelId(),
                        String.valueOf(reservation.getNights()),
                        dateStr
                );
            }
        } catch (Exception e) {
            DisplayUtils.displayError(e.getMessage());
        }
    }

    private void handleUpdateProfile() {
        DisplayUtils.displayHeader("MODIFIER MON PROFIL");

        try {
            Client currentUser = authService.getCurrentUser();
            DisplayUtils.displayInfo("Profil actuel:");
            DisplayUtils.displayInfo("Nom: " + currentUser.getFullName());
            DisplayUtils.displayInfo("Email: " + currentUser.getEmail());

            String newFullName = ConsoleUtils.readName("Nouveau nom complet: ");
            String newEmail = ConsoleUtils.readEmail("Nouvel email: ");

            boolean success = authService.updateProfile(newFullName, newEmail);
            if (success) {
                DisplayUtils.displaySuccess("Profil mis à jour avec succès!");
            }
        } catch (Exception e) {
            DisplayUtils.displayError(e.getMessage());
        }
    }

    private void handleChangePassword() {
        DisplayUtils.displayHeader("CHANGER MOT DE PASSE");

        try {
            String oldPassword = ConsoleUtils.readPassword("Ancien mot de passe: ");
            String newPassword = ConsoleUtils.readPassword("Nouveau mot de passe (min 6 caractères): ");

            boolean success = authService.changePassword(oldPassword, newPassword);
            if (success) {
                DisplayUtils.displaySuccess("Mot de passe changé avec succès!");
            }
        } catch (Exception e) {
            DisplayUtils.displayError(e.getMessage());
        }
    }

    private void handleLogout() {
        authService.logout();
        DisplayUtils.displaySuccess("Déconnexion réussie!");
    }
}
