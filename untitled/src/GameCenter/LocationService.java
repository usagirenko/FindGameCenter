package GameCenter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class LocationService {
        private static final String API_KEY = "AIzaSyB2_2t8UdImNogfbeNS3kGNK9NOamdvKLQ"; // Replace with your Google API key

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the Earth in kilometers
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in kilometers
    }

    public static String getStaticMapUrl(double latitude, double longitude) {
        return String.format(
                "https://maps.googleapis.com/maps/api/staticmap?center=%f,%f&markers=color:red|%f,%f&zoom=16&size=480x360&key=AIzaSyDmzdfHB3r-NsYkBLPHj01emqr6NRZPckc&language=en",
                latitude, longitude, latitude, longitude
        );
    }

        public static double[] getUserLocation() throws Exception {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://www.googleapis.com/geolocation/v1/geolocate?key=" + API_KEY)
                    .post(RequestBody.create("", null))
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(response.body().string());
                double lat = rootNode.path("location").path("lat").asDouble();
                double lng = rootNode.path("location").path("lng").asDouble();

                return new double[]{lat, lng};
            }
        }
    }

