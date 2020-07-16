package ucl.ac.uk.main;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String address;
    private String city;
    private String country;
    private double lat;
    private double lng;
    private Coordinates coordinates;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.coordinates = new Coordinates();
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public String getAddress(){
        return this.address;
    }

    public String getCity(){
        return this.city;
    }

    public String getCountry(){
        return this.country;
    }

    public double getLat(){
        return this.lat;
    }

    public double getLng(){
        return this.lng;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void registerAddress(String address){
        try {
            ArrayList<Double> result = coordinates.calculateCoords(address);
            this.lat = result.get(0);
            this.lng = result.get(1);
        }catch (Exception e){
            System.out.println("Calculating coordinates for a user failed.");
        }
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setCity(String city){
        this.city = city;
    }

    public void setCountry(String country){
        this.country = country;
    }

    public void setLat(double lat){
        this.lat = lat;
    }

    public void setLng(double lng){
        this.lng = lng;
    }
}
