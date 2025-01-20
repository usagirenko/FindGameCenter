package GameCenter;

import javafx.collections.ObservableList;

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
    private double distance;
    private String mapUrl;
    private List<List<Cabinet>> cabinets;


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


    public void addCabinet(Cabinet cabinet, int quantity) {
        List<Cabinet> cabinetList = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            cabinetList.add(cabinet);
        }
        cabinets.add(cabinetList);
    }


    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<List<Cabinet>> getCabinets() {
        return cabinets;
    }


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


    public void printCabinets() {
        for (List<Cabinet> cabinetList : cabinets) {
            for (Cabinet cabinet : cabinetList) {
                System.out.println(cabinet);
            }
        }
    }

    public void printCabinets(ObservableList<String> list) {
        for (List<Cabinet> cabinetGroup : cabinets) {
            for (Cabinet cabinet : cabinetGroup) {
                list.add(cabinet.toString());
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

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
    }

    public void setCabinets(List<List<Cabinet>> cabinets) {
        this.cabinets = cabinets;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}