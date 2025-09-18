import Model.Client;
import Services.AuthService;
import repository.MemoryClient;


public class Main {
        public static void main(String[] args) {

                MemoryClient clientRepository = new MemoryClient();

                AuthService authService = new AuthService(clientRepository);

                System.out.println("=== Testing Hotel Management System ===\n");

                System.out.println("1. Testing User Registration:");
                boolean registerResult1 = authService.register("John Doe", "john@example.com", "securePass123");
                boolean registerResult2 = authService.register("guezadi", "guezadi@gmail.com", "Pass1234");
                boolean registerResult3 = authService.register("Alice Smith", "alice@example.com", "alice123");

                System.out.println("John Doe registration: " + (registerResult1 ? "SUCCESS" : "FAILED"));
                System.out.println("Guezadi registration: " + (registerResult2 ? "SUCCESS" : "FAILED"));
                System.out.println("Alice Smith registration: " + (registerResult3 ? "SUCCESS" : "FAILED"));

                try {
                        boolean duplicateRegister = authService.register("John Smith", "john@example.com", "newpass");
                        System.out.println("Duplicate email registration: "
                                        + (duplicateRegister ? "SUCCESS (ERROR!)" : "FAILED (CORRECT)"));
                } catch (IllegalArgumentException e) {
                        System.out.println("Duplicate email registration: FAILED (CORRECT) - " + e.getMessage());
                }

                System.out.println("\n2. Testing User Login:");

                boolean loginSuccess1 = authService.login("john@example.com", "securePass123");
                if (loginSuccess1) {
                        System.out.println("Login SUCCESS for: " + authService.getCurrentUser().getFullName());
                } else {
                        System.out.println("Login FAILED for john@example.com");
                }

                authService.logout();

                boolean loginSuccess2 = authService.login("guezadi@gmail.com", "wrongpass");
                if (loginSuccess2) {
                        System.out.println("Login SUCCESS for: " + authService.getCurrentUser().getFullName());
                } else {
                        System.out.println("Login FAILED for guezadi@gmail.com (wrong password) - CORRECT");
                }

                authService.logout();

                boolean loginSuccess3 = authService.login("guezadi@gmail.com", "Pass1234");
                if (loginSuccess3) {
                        System.out.println("Login SUCCESS for: " + authService.getCurrentUser().getFullName());
                } else {
                        System.out.println("Login FAILED for guezadi@gmail.com");
                }

                authService.logout();

                boolean loginSuccess4 = authService.login("notexist@example.com", "anypass");
                if (loginSuccess4) {
                        System.out.println("Login SUCCESS for: " + authService.getCurrentUser().getFullName());
                } else {
                        System.out.println("Login FAILED for non-existent email - CORRECT");
                }

                System.out.println("\n3. Testing User Profile Operations:");

                authService.login("john@example.com", "securePass123");
                Client currentUser = authService.getCurrentUser();
                if (currentUser != null) {
                        System.out.println("Current logged user: " + currentUser.getFullName());
                        System.out.println("User email: " + currentUser.getEmail());
                        System.out.println("User ID: " + currentUser.getId());
                }

                System.out.println("\n4. Testing Profile Update:");
                try {
                        boolean updateResult = authService.updateProfile("John Doe Updated",
                                        "john.updated@example.com");
                        System.out.println("Profile update: " + (updateResult ? "SUCCESS" : "FAILED"));
                        System.out.println("Updated user: " + authService.getCurrentUser().getFullName());
                        System.out.println("Updated email: " + authService.getCurrentUser().getEmail());
                } catch (Exception e) {
                        System.out.println("Profile update error: " + e.getMessage());
                }

                System.out.println("\n5. Testing Password Change:");
                try {
                        boolean passwordResult = authService.changePassword("securePass123", "newPassword123");
                        System.out.println("Password change: " + (passwordResult ? "SUCCESS" : "FAILED"));
                } catch (Exception e) {
                        System.out.println("Password change error: " + e.getMessage());
                }

                authService.logout();
                boolean newPasswordLogin = authService.login("john.updated@example.com", "newPassword123");
                System.out.println("Login with new password: " + (newPasswordLogin ? "SUCCESS" : "FAILED"));

                System.out.println("\n6. Testing Logout:");
                authService.logout();
                System.out.println("User logged out. Current user: " +
                                (authService.getCurrentUser() == null ? "null (SUCCESS)" : "still logged in (ERROR)"));
                System.out.println("Is connected: " + authService.isConnect());

                System.out.println("\n=== Testing Complete ===");
        }
}