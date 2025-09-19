package Utils;



public class DisplayUtils {



    public static void displayTableHeader(String... columns) {
        String header = "";
        for (String column : columns) {
            header += String.format("%-38s", column);
        }
        System.out.println(header);
        System.out.println("-".repeat(header.length()));
    }


    public static void displayTableRow(String... values) {
        String row ="";
        for (String value : values) {
            row += String.format("%-38s", value != null ? value : "N/A");
        }
        System.out.println(row);
    }


    public static void displayMenu(String title, String... options) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println(title);
        System.out.println("=".repeat(50));
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.println("=".repeat(50));
    }


    public static void displayHeader(String title) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println(title.toUpperCase());
        System.out.println("=".repeat(50));
    }


    public static void displaySuccess(String message) {
        System.out.println( message);
    }


    public static void displayError(String message) {
        System.out.println("ERREUR: " + message);
    }


   public static void displayInfo(String message) {
        System.out.println( message);
    }



}
