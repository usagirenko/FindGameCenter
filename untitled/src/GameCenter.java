import java.util.ArrayList;
import java.util.List;
public class GameCenter {
    // Fields to store game center details
    private String name;
    private String address;
    private String area;
    private int storeId;
    private int regionId;
    private double latitude;
    private double longitude;
    private String mapUrl;
    private List<List<Cabinet>> cabinets;

    // Constructor to initialize game center details


    public GameCenter(String name, String address, String area,int regionId, int storeId, double latitude, double longitude, String mapUrl) {
        this.name = name;
        this.address = address;
        this.area = area;
        this.regionId = regionId;
        this.storeId = storeId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.mapUrl = mapUrl;
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

    public String getAddress() {
        return address;
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

    public String getArea() {
        return area;
    }

    public int getStoreId() {
        return storeId;
    }

    public int getRegionId() {
        return regionId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    @Override
    public String toString() {
        return "================================================\n" +
                "Name: " + name  +
                "\nAddress: " + address +
                "\nArea: " + area +
                "\nRegion ID: " + regionId +
                "\nStore ID: " + storeId +
                "\nLatitude: " + latitude +
                "\nLongitude: " + longitude +
                "\nMap URL: " + mapUrl +"\n";
    }
}