
/*
public class Cabinet {
    private String name = "Unknown";
    private String description = "Unknown";
    private String manufacturer= "Unknown";
    private String version= "Unknown";
    private String genre= "Unknown";
    private String status = "Unknown";
    private int pricePerPlay = 100;

    public Cabinet(String name, String description, String manufacturer, String version, String genre, String status,int pricePerPlay) {
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.version = version;
        this.genre = genre;
        this.status = status;
        this.pricePerPlay = pricePerPlay;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getVersion() {
        return version;
    }

    public String getGenre() {
        return genre;
    }

    public String getStatus() {
        return status;
    }

    public int getPricePerPlay() {
        return pricePerPlay;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPricePerPlay(int pricePerPlay) {
        this.pricePerPlay = pricePerPlay;
    }

    public boolean isOperational() {
        return status.equals("Avaliable");
    }

    @Override
    public String toString() {
        return  "================================================\n" +
                "Name: " + name  +
                "\nDescription: " + description +
                "\nManufacturer: " + manufacturer  +
                "\nVersion: " + version  +
                "\nGenre: " + genre  +
                "\nStatus: " + status  +
                "\nPricePerPlay: " + pricePerPlay + " yen";
    }

}

 */
public class Cabinet {
    // Fields to store cabinet details
    private String name = "Unknown";
    private String description = "Unknown";
    private String manufacturer= "Unknown";
    private String version= "Unknown";
    private String genre= "Unknown";
    private String status = "Unknown";
    private int pricePerPlay = 100;

    // Constructor to initialize cabinet details
    public Cabinet(String name, String description, String manufacturer, String version, String genre, String status, int pricePerPlay) {
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.version = version;
        this.genre = genre;
        this.status = status;
        this.pricePerPlay = pricePerPlay;
    }

    // Getter methods for cabinet details
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getVersion() {
        return version;
    }

    public String getGenre() {
        return genre;
    }

    public String getStatus() {
        return status;
    }

    public int getPricePerPlay() {
        return pricePerPlay;
    }

    // Setter methods for cabinet details
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPricePerPlay(int pricePerPlay) {
        this.pricePerPlay = pricePerPlay;
    }

    // Method to check if the cabinet is operational
    public boolean isOperational() {
        return status.equals("Avaliable");
    }

    // Method to return a string representation of the cabinet
    @Override
    public String toString() {
        return  "================================================\n" +
                "Name: " + name  +
                "\nDescription: " + description +
                "\nManufacturer: " + manufacturer  +
                "\nVersion: " + version  +
                "\nGenre: " + genre  +
                "\nStatus: " + status  +
                "\nPricePerPlay: " + pricePerPlay + " yen";
    }
}