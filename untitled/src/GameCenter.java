import java.util.ArrayList;
import java.util.List;
public class GameCenter {
    // Fields to store game center details
    private String name;
    private String location;
    private List<List<Cabinet>> cabinets;

    // Constructor to initialize game center details
    public GameCenter(String name, String location) {
        this.name = name;
        this.location = location;
        this.cabinets = new ArrayList<>();
    }

    // Method to add a cabinet to the game center
    public void addCabinet(Cabinet cabinet, int quantity) {
        List<Cabinet> cabinetList = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            cabinetList.add(cabinet);
        }
        cabinets.add(cabinetList);
    }

    // Getter methods for game center details
    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public List<List<Cabinet>> getCabinets() {
        return cabinets;
    }

    // Method to check if the game center has a specific cabinet
    public boolean hasCabinet(String cabinetName) {
        for (List<Cabinet> cabinetList : cabinets) {
            for (Cabinet cabinet : cabinetList) {
                if (cabinet.getName().equalsIgnoreCase(cabinetName)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Method to print all cabinets in the game center
    public void printCabinets() {
        for (List<Cabinet> cabinetList : cabinets) {
            for (Cabinet cabinet : cabinetList) {
                System.out.println(cabinet);
            }
        }
    }
}