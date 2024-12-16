import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    // Method to initialize game centers with cabinets
    private static List<GameCenter> initializeGameCenters() {
        List<GameCenter> gameCenters = new ArrayList<>();
        // Create and add cabinets to the first game center
        GameCenter center1 = new GameCenter("Round one Umeda", "4-16 Komatsubaracho, Kita Ward, Osaka, 530-0018, Japan");
        center1.addCabinet(new Maimai(), 8);
        center1.addCabinet(new Chunithm(), 8);
        center1.addCabinet(new Ongeki(), 6);
        center1.addCabinet(new TaikoNoTatsujin(), 4);

        // Create and add cabinets to the second game center
        GameCenter center2 = new GameCenter("Feedy Diner&Arcade", "Japan, 〒567-0033 Osaka, Ibaraki, Matsugamotocho, 8−30−3 イオンモール茨木 4階");
        center2.addCabinet(new Maimai(), 2);
        center2.addCabinet(new Chunithm(), 2);
        center2.addCabinet(new Ongeki(), 2);
        center2.addCabinet(new TaikoNoTatsujin(), 2);

        // Add game centers to the list
        gameCenters.add(center1);
        gameCenters.add(center2);

        return gameCenters;
    }

    // Method to list all game centers and allow user to view cabinets
    private static void listGameCenters(List<GameCenter> gameCenters, Scanner scanner) {
        while (true) {
            System.out.println("\n=== List of Game Centers ===");
            for (int i = 0; i < gameCenters.size(); i++) {
                System.out.printf("%d. %s: %s%n", i + 1, gameCenters.get(i).getName(), gameCenters.get(i).getLocation());
            }
            System.out.print("Enter the number of the game center to view its cabinets (or 0 to go back): ");

            String input = scanner.nextLine();
            try {
                int index = Integer.parseInt(input) - 1;
                if (index >= 0 && index < gameCenters.size()) {
                    displayCabinets(gameCenters.get(index));
                } else if (index == -1) {
                    // Go back to the main menu
                    System.out.println("Returning to main menu...");
                    break;
                } else {
                    System.out.println("Invalid game center number. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    // Method to display cabinets in a game center
    private static void displayCabinets(GameCenter gameCenter) {
        System.out.printf("\n=== Cabinets in %s ===%n", gameCenter.getName());
        gameCenter.printCabinets();
        System.out.println('\n');
    }

    // Method to show available cabinets across all game centers
    private static void showAvailableCabinets(List<GameCenter> gameCenters, Scanner scanner) {
        System.out.println("=== List of Available Cabinets ===");
        List<String> availableCabinets = new ArrayList<>();
        for (GameCenter gameCenter : gameCenters) {
            for (List<Cabinet> cabinets : gameCenter.getCabinets()) {
                for (Cabinet cabinet : cabinets) {
                    if (cabinet.getStatus().equalsIgnoreCase("Operational") && !availableCabinets.contains(cabinet.getName())) {
                        availableCabinets.add(cabinet.getName());
                    }
                }
            }
        }
        if (availableCabinets.isEmpty()) {
            System.out.println("No available cabinets found.");
            return;
        }
        for (int i = 0; i < availableCabinets.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, availableCabinets.get(i));
        }
    }

    // Method to search for game centers by cabinet name
    private static void searchGameCenterByCabinet(List<GameCenter> gameCenters, Scanner scanner) {
        showAvailableCabinets(gameCenters, scanner);
        System.out.print("Enter the name of the cabinet to search for (Case-insensitive): ");
        String cabinetName = scanner.nextLine();
        boolean found = false;
        List<GameCenter> hasCabinet = new ArrayList<>();
        for (GameCenter gameCenter : gameCenters) {
            if (gameCenter.hasCabinet(cabinetName)) {
                System.out.printf("%s is found in the game center %s%n", cabinetName, gameCenter.getName());
                found = true;
                hasCabinet.add(gameCenter);
            }
        }
        if (!found) {
            System.out.printf("%s is not found in any of the game centers!%n", cabinetName);
            return;
        }
        while (true) {
            System.out.print("Do you want to view the cabinets in the game center(s)? (y/n): ");
            String viewCabinets = scanner.nextLine();
            if (viewCabinets.equalsIgnoreCase("y")) {
                for (GameCenter gameCenter : hasCabinet) {
                    displayCabinets(gameCenter);
                }
                break;
            } else if (viewCabinets.equalsIgnoreCase("n")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }
    }

    // Main method to run the CLI application
    public static void main(String[] args) {
        List<GameCenter> gameCenters = initializeGameCenters();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("\n=== Game Center CLI ===");
            System.out.println("1. List all game centers");
            System.out.println("2. Search game center by cabinet");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    listGameCenters(gameCenters, scanner);
                    break;
                case "2":
                    searchGameCenterByCabinet(gameCenters, scanner);
                    break;
                case "3":
                    System.out.println("Exiting the CLI. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
}