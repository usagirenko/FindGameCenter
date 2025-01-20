package GameCenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class Controller {
    @FXML
    private VBox mainMenu;
    @FXML
    private VBox cabinetList;
    @FXML
    private VBox areaList;
    @FXML
    private VBox centerList;
    @FXML
    private VBox centerDetails;
    @FXML
    private ListView<String> cabinetListView;
    @FXML
    private ListView<String> areaListView;
    @FXML
    private ListView<String> centerListView;
    @FXML
    private ListView<String> cabinetDetailsListView;
    @FXML
    private ListView<String> centerDetailsListView;


    private List<GameCenter> gameCenters;
    private String selectedCabinet; // To keep track of selected cabinet

    @FXML
    public void initialize() {
        String jsonDirectoryPath = "/home/latenci/study/FindGameCenter/data";
        gameCenters = Main.loadGameCentersFromJson(jsonDirectoryPath);
        showMainMenu();
    }

    private void hideAllScreens() {
        mainMenu.setVisible(false);
        cabinetList.setVisible(false);
        areaList.setVisible(false);
        centerList.setVisible(false);
        centerDetails.setVisible(false);
    }

    private void showMainMenu() {
        hideAllScreens();
        mainMenu.setVisible(true);
        clearSelections();
    }

    private void clearSelections() {
        cabinetListView.getSelectionModel().clearSelection();
        areaListView.getSelectionModel().clearSelection();
        centerListView.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleSearchByCabinet() {
        hideAllScreens();
        cabinetList.setVisible(true);

        Set<String> cabinets = new LinkedHashSet<>();
        for (GameCenter center : gameCenters) {
            for (List<Cabinet> cabinetGroup : center.getCabinets()) {
                for (Cabinet cabinet : cabinetGroup) {
                    cabinets.add(cabinet.getName());
                }
            }
        }

        ObservableList<String> cabinetListItems = FXCollections.observableArrayList(cabinets);
        cabinetListView.setItems(cabinetListItems);
        cabinetListView.getSelectionModel().clearSelection();

        cabinetListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectedCabinet = newVal;
                showAreasForCabinet(newVal);
            }
        });
    }

    @FXML
    private void handleSearchByArea() {
        hideAllScreens();
        areaList.setVisible(true);

        Set<String> countries = new TreeSet<>();
        for (GameCenter center : gameCenters) {
            if (center.getRegionId() < 1000) {
                countries.add("Japan");
            } else {
                countries.add(center.getArea());
            }
        }

        ObservableList<String> countryListItems = FXCollections.observableArrayList(countries);
        areaListView.setItems(countryListItems);
        areaListView.getSelectionModel().clearSelection();

        areaListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                if (newVal.equals("Japan")) {
                    showRegionsInJapan();
                } else {
                    showGameCentersByArea(newVal);
                }
            }
        });
    }

    private void showRegionsInJapan() {
        hideAllScreens();
        areaList.setVisible(true);

        Set<String> regions = new TreeSet<>();
        for (GameCenter center : gameCenters) {
            if (center.getRegionId() < 1000) {
                regions.add(center.getArea());
            }
        }

        ObservableList<String> regionListItems = FXCollections.observableArrayList(regions);
        areaListView.setItems(regionListItems);
        areaListView.getSelectionModel().clearSelection();

        areaListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                showGameCentersByArea(newVal);
            }
        });
    }

    @FXML
    private void handleBackToMainMenu() {
        showMainMenu();
    }

    private void showAreasForCabinet(String cabinetName) {
        hideAllScreens();
        areaList.setVisible(true);

        Set<String> areas = new TreeSet<>();
        for (GameCenter center : gameCenters) {
            if (center.hasCabinet(cabinetName)) {
                areas.add(center.getArea());
            }
        }

        ObservableList<String> areaListItems = FXCollections.observableArrayList(areas);
        areaListView.setItems(areaListItems);
        areaListView.getSelectionModel().clearSelection();

        areaListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                showGameCentersByAreaAndCabinet(newVal, cabinetName);
            }
        });
    }

    private void showGameCentersByArea(String area) {
        hideAllScreens();
        centerList.setVisible(true);

        List<GameCenter> centersInArea = new ArrayList<>();
        for (GameCenter center : gameCenters) {
            if (center.getArea().equals(area)) {
                centersInArea.add(center);
            }
        }

        ObservableList<String> centerListItems = FXCollections.observableArrayList();
        for (GameCenter center : centersInArea) {
            centerListItems.add(center.getName());
        }

        centerListView.setItems(centerListItems);
        centerListView.getSelectionModel().clearSelection();

        centerListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                GameCenter selectedCenter = centersInArea.stream()
                        .filter(c -> c.getName().equals(newVal))
                        .findFirst()
                        .orElse(null);
                if (selectedCenter != null) {
                    try {
                        showGameCenterDetails(selectedCenter);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private void showGameCentersByAreaAndCabinet(String area, String cabinetName) {
        hideAllScreens();
        centerList.setVisible(true);

        List<GameCenter> centersWithCabinet = new ArrayList<>();
        for (GameCenter center : gameCenters) {
            if (center.getArea().equals(area) && center.hasCabinet(cabinetName)) {
                centersWithCabinet.add(center);
            }
        }

        ObservableList<String> centerListItems = FXCollections.observableArrayList();
        for (GameCenter center : centersWithCabinet) {
            centerListItems.add(center.getName());
        }

        centerListView.setItems(centerListItems);
        centerListView.getSelectionModel().clearSelection();

        centerListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                GameCenter selectedCenter = centersWithCabinet.stream()
                        .filter(c -> c.getName().equals(newVal))
                        .findFirst()
                        .orElse(null);
                if (selectedCenter != null) {
                    try {
                        showGameCenterDetails(selectedCenter);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private void showGameCenterDetails(GameCenter center) throws MalformedURLException {
        hideAllScreens();

        centerDetails.setVisible(true);


        ObservableList<String> centerDetailsItems = FXCollections.observableArrayList(center.toString().split("\n"));
        centerDetailsListView.setItems(centerDetailsItems);

        ObservableList<String> cabinetDetailsItems = FXCollections.observableArrayList();
        for (List<Cabinet> cabinetGroup : center.getCabinets()) {
            for (Cabinet cabinet : cabinetGroup) {
                cabinetDetailsItems.add(cabinet.getName() + " (" + cabinet.getStatus() + ")");
            }
        }

        cabinetDetailsListView.setItems(cabinetDetailsItems);
    }

    @FXML
    private void handleFindNearestGameCenters() {
        hideAllScreens();
        centerList.setVisible(true);

        double[] userLocation = getUserLocation(); // Implement this method to get the user's location
        double userLat = userLocation[0];
        double userLng = userLocation[1];

        List<GameCenter> sortedCenters = new ArrayList<>(gameCenters);
        sortedCenters.sort(Comparator.comparingDouble(center -> LocationService.calculateDistance(userLat, userLng, center.getLatitude(), center.getLongitude())));

        ObservableList<String> centerListItems = FXCollections.observableArrayList();
        for (int i = 0; i < Math.min(10, sortedCenters.size()); i++) {
            GameCenter center = sortedCenters.get(i);
            centerListItems.add(center.getName() + " (" + LocationService.calculateDistance(userLat, userLng, center.getLatitude(), center.getLongitude()) + " km)");
        }

        centerListView.setItems(centerListItems);
        centerListView.getSelectionModel().clearSelection();

        centerListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                GameCenter selectedCenter = sortedCenters.stream()
                        .filter(c -> c.getName().equals(newVal.split(" \\(")[0]))
                        .findFirst()
                        .orElse(null);
                if (selectedCenter != null) {
                    try {
                        showGameCenterDetails(selectedCenter);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private double[] getUserLocation() {
        double[] userLocation = new double[2];
        try {
            userLocation = LocationService.getUserLocation();
            double userLat = userLocation[0];
            double userLng = userLocation[1];
            System.out.printf("Your location: %f, %f%n", userLat, userLng);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userLocation;
    }
}