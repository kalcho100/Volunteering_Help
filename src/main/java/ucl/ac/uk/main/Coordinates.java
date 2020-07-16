package ucl.ac.uk.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

import java.util.ArrayList;

public class Coordinates {
    private final String GOOGLE_API_KEY;

    public Coordinates(){
        this.GOOGLE_API_KEY = "AIzaSyDjb1hbAqOUDqifrd844jS82F5X_x0uxls";
    }

    public ArrayList<Double> calculateCoords(String address) throws Exception{
        ArrayList<Double> result = new ArrayList<>();
        GeoApiContext context = new GeoApiContext.Builder().apiKey(GOOGLE_API_KEY).build();
        GeocodingResult[] results =  GeocodingApi.geocode(context, address).await();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result.add(Double.parseDouble(gson.toJson(results[0].geometry.location.lat)));
        result.add(Double.parseDouble(gson.toJson(results[0].geometry.location.lng)));
        return result;
    }

    public double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        if(dist > 0.9999999999999 && dist < 1){
            dist = 1;
        }
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return dist * 1.609344;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    private ArrayList<Double> calculateEndPoint(double lat, double lng, double range, double bearing)
    {
        double DegreesToRadians = Math.PI/180.0;
        double RadiansToDegrees = 180.0/ Math.PI;
        double EarthRadius = 6378137.0;
        double latA = lat * DegreesToRadians;
        double lonA = lng * DegreesToRadians;
        double angularDistance = range / EarthRadius;
        double trueCourse = bearing * DegreesToRadians;

        double aLat = Math.asin(
                Math.sin(latA) * Math.cos(angularDistance) +
                        Math.cos(latA) * Math.sin(angularDistance) * Math.cos(trueCourse));

        double dlon = Math.atan2(
                Math.sin(trueCourse) * Math.sin(angularDistance) * Math.cos(latA),
                Math.cos(angularDistance) - Math.sin(latA) * Math.sin(aLat));

        double lon = ((lonA + dlon + Math.PI) % (Math.PI*2)) - Math.PI;

        ArrayList<Double> result = new ArrayList<>();
        result.add(aLat * RadiansToDegrees);
        result.add(lon * RadiansToDegrees);
        return result;
    }

    public ArrayList<Double> endPoints(double lat, double lng, int radius){
        ArrayList<Double> result = new ArrayList<>();
        double latMax = calculateEndPoint(lat, lng, radius * 1000, 0).get(0);
        double latMin = calculateEndPoint(lat, lng, radius * 1000, -180).get(0);
        double lngMax = calculateEndPoint(lat, lng, radius * 1000, 90).get(1);
        double lngMin = calculateEndPoint(lat, lng, radius * 1000, -90).get(1);
        result.add(latMax);
        result.add(latMin);
        result.add(lngMax);
        result.add(lngMin);
        return result;
    }
}
