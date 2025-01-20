import java.util.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
public class Main {
    private static void searchGameCenterByArea(List<GameCenter> gameCenters, Scanner scanner) {
        // First, collect all unique areas and their region IDs
        Map<String, String> areaMap = new TreeMap<>(); // Using TreeMap for sorted keys
        for (GameCenter center : gameCenters) {
            if (center.getRegionId() < 1000) {
               continue; // Skip Japan
            }
            String area = center.getArea();
            String regionId = String.valueOf(center.getRegionId());
            if (!areaMap.containsKey(area)) {
                areaMap.put(area, regionId);
            }
        }
         areaMap.put("Japan", "1000"); // Add Japan to the end

        // Display areas
        System.out.println("\n=== Available Areas ===");
        List<String> areas = new ArrayList<>(areaMap.keySet());
        for (int i = 0; i < areas.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, areas.get(i));
        }

        // Get user selection
        while (true) {
            System.out.print("Enter the number of the area (or 0 to go back): ");
            try {
                int selection = Integer.parseInt(scanner.nextLine());
                if (selection == 0) return;
                if (selection > 0 && selection <= areas.size()) {
                    String selectedArea = areas.get(selection - 1);
                    String regionId = areaMap.get(selectedArea);


                    if (Integer.parseInt(regionId) == 1000) {
                        searchJapaneseRegions(gameCenters, scanner);
                    } else {
                        // Display all game centers in the selected area
                        displayGameCentersByArea(gameCenters, selectedArea);
                    }
                    break;
                } else {
                    System.out.println("Invalid selection. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    // Method to handle Japanese regions
    private static void searchJapaneseRegions(List<GameCenter> gameCenters, Scanner scanner) {
        // Collect all unique areas (regions) within Japan
        Set<String> regions = new TreeSet<>();
        for (GameCenter center : gameCenters) {
            if (center.getRegionId() < 1000) { // Japan region IDs are less than 1000
                regions.add(center.getArea());
            }
        }

        // Display regions
        System.out.println("\n=== Available Regions in Japan ===");
        List<String> regionList = new ArrayList<>(regions);
        for (int i = 0; i < regionList.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, regionList.get(i));
        }

        // Get user selection
        while (true) {
            System.out.print("Enter the number of the region (or 0 to go back): ");
            try {
                int selection = Integer.parseInt(scanner.nextLine());
                if (selection == 0) return;
                if (selection > 0 && selection <= regionList.size()) {
                    String selectedRegion = regionList.get(selection - 1);
                    displayGameCentersByArea(gameCenters, selectedRegion);
                    break;
                } else {
                    System.out.println("Invalid selection. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    // Method to display game centers by area
    private static void displayGameCentersByArea(List<GameCenter> gameCenters, String area) {
        System.out.printf("\n=== Game Centers in %s ===%n", area);
        boolean found = false;
        List<GameCenter> gameCentersInArea = new ArrayList<>();
        for (GameCenter center : gameCenters) {
            if (center.getArea().equals(area)) {
                gameCentersInArea.add(center);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No game centers found in this area.");
            return;
        }
        for(int i = 0; i < gameCentersInArea.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, gameCentersInArea.get(i).getName());
        }

        // Get user selection
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter the number of the game center to view details (or 0 to go back): ");
            try {
                int selection = Integer.parseInt(scanner.nextLine());
                if (selection == 0) return;
                if (selection > 0 && selection <= gameCentersInArea.size()) {
                    GameCenter selectedCenter = gameCentersInArea.get(selection - 1);
                    displayCabinets(selectedCenter);
                    break;
                } else {
                    System.out.println("Invalid selection. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

    }


    // Method to display cabinets in a game center
    private static void displayCabinets(GameCenter gameCenter) {
        System.out.printf("\n=== Cabinets in %s ===%n", gameCenter.getName());
        System.out.printf(gameCenter.toString());
        gameCenter.printCabinets();
        System.out.println();
        System.out.print("Press enter to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    // Method to show available cabinets across all game centers
    private static Map showAvailableCabinets(List<GameCenter> gameCenters, Scanner scanner) {
        System.out.println("=== List of Available Cabinets ===");
        List<String> availableCabinets = new ArrayList<String>();
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
            return null;
        }
        Map<Integer,String> availableCabinetsMap = new HashMap<>();
        for (int i = 0; i < availableCabinets.size(); i++) {
            availableCabinetsMap.put(i, availableCabinets.get(i));
            System.out.printf("%d. %s%n", i + 1, availableCabinets.get(i));
        }
        return availableCabinetsMap;
    }

    // Method to search for game centers by cabinet name
    private static void searchGameCenterByCabinet(List<GameCenter> gameCenters, Scanner scanner) {
        Map<Integer, String> availableCabinetsMap = showAvailableCabinets(gameCenters, scanner);
        if (availableCabinetsMap == null) {
            return;
        }

        System.out.print("Enter the number of the cabinet to search for: ");
        int cabinetIndex = Integer.parseInt(scanner.nextLine()) - 1;
        String cabinetName = availableCabinetsMap.get(cabinetIndex);

        // Collect all unique areas
        Set<String> areas = new TreeSet<>();
        for (GameCenter center : gameCenters) {
            if (center.hasCabinet(cabinetName)) {
                areas.add(center.getArea());
            }
        }

        // Display areas
        System.out.println("\n=== Available Areas ===");
        List<String> areaList = new ArrayList<>(areas);
        for (int i = 0; i < areaList.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, areaList.get(i));
        }

        // Get user selection
        while (true) {
            System.out.print("Enter the number of the area (or 0 to go back): ");
            try {
                int selection = Integer.parseInt(scanner.nextLine());
                if (selection == 0) return;
                if (selection > 0 && selection <= areaList.size()) {
                    String selectedArea = areaList.get(selection - 1);
                    displayGameCentersByAreaAndCabinet(gameCenters, selectedArea, cabinetName);
                    break;
                } else {
                    System.out.println("Invalid selection. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static void displayGameCentersByAreaAndCabinet(List<GameCenter> gameCenters, String area, String cabinetName) {
        System.out.printf("\n=== Game Centers in %s with %s ===%n", area, cabinetName);
        List<GameCenter> gameCentersInArea = new ArrayList<>();
        for (GameCenter center : gameCenters) {
            if (center.getArea().equals(area) && center.hasCabinet(cabinetName)) {
                gameCentersInArea.add(center);
            }
        }
        if (gameCentersInArea.isEmpty()) {
            System.out.println("No game centers found in this area with the specified cabinet.");
            return;
        }
        for (int i = 0; i < gameCentersInArea.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, gameCentersInArea.get(i).getName());
        }

        // Get user selection
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter the number of the game center to view details (or 0 to go back): ");
            try {
                int selection = Integer.parseInt(scanner.nextLine());
                if (selection == 0) return;
                if (selection > 0 && selection <= gameCentersInArea.size()) {
                    GameCenter selectedCenter = gameCentersInArea.get(selection - 1);
                    displayCabinets(selectedCenter);
                    break;
                } else {
                    System.out.println("Invalid selection. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static List<GameCenter> loadGameCentersFromJson(String directoryPath) {
        ObjectMapper mapper = new ObjectMapper();
        // Use a Map to track unique game centers by their store_id
        Map<Integer, GameCenter> gameCenterMap = new HashMap<>();
        File directory = new File(directoryPath);

        if (!directory.isDirectory()) {
            System.err.println("Invalid directory path!");
            return new ArrayList<>();
        }

        for (File file : directory.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".json")) {
                try {
                    JsonNode rootNode = mapper.readTree(file);

                    // Process either array or single object
                    if (rootNode.isArray()) {
                        processGameCenterNodes(rootNode, gameCenterMap);
                    } else {
                        processGameCenterNode(rootNode, gameCenterMap);
                    }
                } catch (IOException e) {
                    System.err.println("Failed to parse JSON file: " + file.getName());
                    e.printStackTrace();
                }
            }
        }

        // Convert map values to list for return
        return new ArrayList<>(gameCenterMap.values());
    }

    private static void processGameCenterNodes(JsonNode arrayNode, Map<Integer, GameCenter> gameCenterMap) {
        for (JsonNode centerNode : arrayNode) {
            processGameCenterNode(centerNode, gameCenterMap);
        }
    }

    private static void processGameCenterNode(JsonNode node, Map<Integer, GameCenter> gameCenterMap) {
        try {
            int storeId = node.get("store_id").asInt();
            int regionId = node.get("region_id").asInt();
            // Check if we already have this game center
            GameCenter center = gameCenterMap.get(storeId);

            if (center == null) {
                // Create new game center if it doesn't exist
                String name = node.get("name").asText();
                String address = node.get("address").asText();
                String area = node.get("area").asText();
                double latitude = node.get("latitude").asDouble();
                double longitude = node.get("longitude").asDouble();
                String mapUrl = node.get("map_url").asText();

                center = new GameCenter(name, address, area, regionId, storeId, latitude, longitude, mapUrl);
                gameCenterMap.put(storeId, center);
            }

            // Add games to the existing or new center
            addGamesToCenter(node, center);

        } catch (Exception e) {
            System.err.println("Failed to parse GameCenter: " + e.getMessage());
        }
    }

    private static void addGamesToCenter(JsonNode node, GameCenter center) {
        // Handle multiple cabinets if they exist
        JsonNode gamesNode = node.get("games");
        if (gamesNode != null && gamesNode.isArray()) {
            for (JsonNode gameNode : gamesNode) {
                String gameName = gameNode.get("name").asText();
                int quantity = gameNode.get("quantity").asInt(1);
                Cabinet cabinet = createCabinet(gameName);
                if (cabinet != null && !center.hasCabinet(gameName)) {
                    center.addCabinet(cabinet, quantity);
                }
            }
        } else if (node.get("game") != null) {
            // Legacy single game support
            String gameName = node.get("game").asText();
            if (!center.hasCabinet(gameName)) {
                Cabinet cabinet = createCabinet(gameName);
                if (cabinet != null) {
                    center.addCabinet(cabinet, 1);
                }
            }
        }
    }

    private static Cabinet createCabinet(String cabinetName) {
        switch (cabinetName) {
                case "CHUNITHM International Version":
                    return new CHUNITHM_International_Version();
                case "maimai DX International Version":
                    return new maimaiDXInternationalVersion();
                case "オンゲキ":
                    return new Ongeki();
                case "CHUNITHM SUN":
                    return new CHUNITHMSUN();
                case "maimai でらっくす":
                    return new maimaiDx();
                default:
                System.err.println("Unknown cabinet type: " + cabinetName);
                return null; // Or throw an exception if preferred
        }
    }

    public static void main(String[] args) {
        String jsonDirectoryPath = "/home/latenci/study/FindGameCenter/data";
        List<GameCenter> gameCenters = loadGameCentersFromJson(jsonDirectoryPath);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Game Center CLI ===");
            System.out.println("1. Search game center by cabinet");
            System.out.println("2. Search game center by area");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    searchGameCenterByCabinet(gameCenters, scanner);
                    break;
                case "2":
                    searchGameCenterByArea(gameCenters, scanner);
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