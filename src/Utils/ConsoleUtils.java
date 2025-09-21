package Utils;

import java.util.Scanner;
import java.util.UUID;

public class ConsoleUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readNonEmptyString(String prompt) {
        String input;
        do {
            System.out.print(prompt + "(ou 0 pour annuler): ");
            input = scanner.nextLine().trim();
            if("0".equals(input)){
                return null;
            }
            if (input.isEmpty()) {
                System.out.println("Cette valeur ne peut pas être vide. Veuillez réessayer.");
            }
        } while (input.isEmpty());
        return input;
    }

    public static String readEmail(String prompt) {
        String email;
        do {
            //email = readNonEmptyString(prompt);
            System.out.print(prompt + "(ou 0 pour annuler): ");
            email = scanner.nextLine().trim();
            if("0".equals(email)){
                return null;
            }
            if (isInvalidEmail(email)) {
                System.out.println("Email invalide. Veuillez entrer un email valide.");
            }
        } while (isInvalidEmail(email));
        return email.toLowerCase();
    }

    public static String readPassword(String prompt) {
        String password;
        do {
            System.out.print(prompt + "(ou 0 pour annuler): ");
            password = scanner.nextLine();
            if("0".equals(password)){
                return null;
            }
            if (isInvalidPassword(password)) {
                System.out.println("Le mot de passe doit contenir au moins 6 caractères.");
            }
        } while (isInvalidPassword(password));
        return password;
    }

    public static int readPositiveInt(String prompt) {
        int value;
        do {
            System.out.print(prompt + "(ou 0 pour annuler): ");

            try {
                String input = scanner.nextLine().trim();
                if("0".equals(input)){
                    return -1;
                }
                value = Integer.parseInt(input);
                if (value <= 0) {
                    System.out.println("Veuillez entrer un nombre positif.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
                value = -1;
            }
        } while (value <= 0);
        return value;
    }

    public static int readNonNegativeInt(String prompt) {
        int value;
        do {
            System.out.print(prompt + "(ou -1 pour annuler): ");
            try {
                String input = scanner.nextLine().trim();
                value = Integer.parseInt(input);
                if(value == -1){
                    return -1;
                }
                if (value < 0) {
                    System.out.println("Veuillez entrer un nombre non négatif.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
                value = -1;
            }
        } while (value < 0);
        return value;
    }

    public static double readDoubleInRange(String prompt, double min, double max) {
        double value;
        do {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();
                value = Double.parseDouble(input);
                if (value < min || value > max) {
                    System.out.printf("Veuillez entrer une valeur entre %.1f et %.1f.%n", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
                value = min - 1;
            }
        } while (value < min || value > max);
        return value;
    }

    public static UUID readUUID(String prompt) {
        UUID uuid;
        do {
            System.out.print(prompt + "(ou 0 pour annuler): ");
            String input = scanner.nextLine().trim();
            if("0".equals(input)){
                return null;
            }
            try {
                uuid = UUID.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Format UUID invalide. Veuillez réessayer.");
                uuid = null;
            }
        } while (uuid == null);
        return uuid;
    }

    public static int readMenuChoice( int minChoice, int maxChoice) {
        int choice;
        do {
            System.out.println("Votre choix: ");
            try {
                String input = scanner.nextLine().trim();
                choice = Integer.parseInt(input);
                if (choice < minChoice || choice > maxChoice) {
                    System.out.printf("Veuillez choisir entre %d et %d.%n", minChoice, maxChoice);
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
                choice = -1;
            }
        } while (choice < minChoice || choice > maxChoice);
        return choice;
    }



    public static double readRating(String prompt) {
        return readDoubleInRange(prompt, 0.0, 5.0);
    }

    private static boolean isInvalidPassword(String password) {
        return password.length() < 6;
    }

    private static boolean isInvalidEmail(String email) {
        return email == null || !email.contains("@") || !email.contains(".") || email.trim().length() < 5;
    }

    public static boolean isInvalidFullName(String fullName) {
        return  fullName.trim().length() < 3;
    }

    public static String readName(String prompt) {
        String fullName;
        do {
            System.out.print(prompt + "(ou 0 pour annuler): ");
            fullName = scanner.nextLine();
            if("0".equals(fullName)){
                return null;
            }
            if (isInvalidFullName(fullName)) {
                System.out.println("Le nom complet doit contenir au moins 3 caractères .");
            }
        } while (isInvalidFullName(fullName));
        return fullName;
    }

}

