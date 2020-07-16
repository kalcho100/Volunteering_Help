package ucl.ac.uk.main;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Objects;

public class Area {
    private String address;
    private String city;
    private String country;
    private Coordinates coordinates;
    private double lat;
    private double lng;
    private int radius;
    private TimeAvailable timeAvailable;
    private Volunteer areaOf;
    private ArrayList<Request> assignedRequests;

    public Area(String address, String city, String country, int radius){
        this.address = address;
        this.city = city;
        this.country = country;
        this.radius = radius;
        this.coordinates = new Coordinates();
        this.timeAvailable = new TimeAvailable();
        this.assignedRequests = new ArrayList<>();
        this.areaOf = null;
        registerAddress(address + ", " + city + ", " + country);
    }

    public Area(String address, String city, String country){
        this.address = address;
        this.city = city;
        this.country = country;
        this.coordinates = new Coordinates();
        this.timeAvailable = new TimeAvailable();
        this.assignedRequests = new ArrayList<>();
        this.areaOf = null;
    }

    public String getAddress(){
        return address;
    }

    public int getRadius(){
        return radius;
    }

    public double getLat(){
        return this.lat;
    }

    public double getLng(){
        return this.lng;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public TimeAvailable getTimeAvailable() {
        return timeAvailable;
    }

    public String getStartTime(){
        return timeAvailable.getStartTime();
    }

    public String getStartDate(){
        return timeAvailable.getStartDate();
    }

    public String getFinishTime(){
        return timeAvailable.getFinishTime();
    }

    public String getFinishDate(){
        return timeAvailable.getFinishDate();
    }

    public String getStartDateArea(){
        return timeAvailable.getStartDateArea();
    }

    public String getFinishDateArea(){
        return timeAvailable.getFinishDateArea();
    }

    public Volunteer getAreaOf() {
        return areaOf;
    }

    public void setTimeAvailable(int startMonth, int startDate, int startHour, int startMinute, int finishMonth, int finishDate, int finishHour, int finishMinute){
        timeAvailable = new TimeAvailable(startMonth, startDate, startHour, startMinute, finishMonth, finishDate, finishHour, finishMinute);
    }

    public void setStartTimeDate(String time, String date){
        timeAvailable.setStart(time, date);
    }

    public void setFinishTimeDate(String time, String date){
        timeAvailable.setFinish(time, date);
    }

    public Calendar getCalendarStart(){
        return timeAvailable.getCalendarStart();
    }

    public double getMinutesAvailable(){
        return timeAvailable.minutesAvailable();
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void registerAddress(String address){
        try {
            ArrayList<Double> result = coordinates.calculateCoords(address);
            this.lat = result.get(0);
            this.lng = result.get(1);
        }catch (Exception e){
            System.out.println("Failed to calculate coordinates for an area.");
        }
    }

    public void setLat(double lat){
        this.lat = lat;
    }

    public void setLng(double lng){
        this.lng = lng;
    }

    public void setRadius(int radius){
        this.radius =  radius;
    }

    public void setAreaOf(Volunteer areaOf) {
        this.areaOf = areaOf;
    }

    public void addRequest(Request request){
        assignedRequests.add(request);
    }

    public void removeRequest(Request request){
        assignedRequests.remove(request);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Area)) return false;
        Area area = (Area) o;
        return Double.compare(area.getLat(), getLat()) == 0 &&
                Double.compare(area.getLng(), getLng()) == 0 &&
                getRadius() == area.getRadius() &&
                Objects.equals(getAreaOf(), area.getAreaOf());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLat(), getLng(), getRadius(), getAreaOf());
    }

    public Iterator<Request> getRequestsIterator(){
        return  assignedRequests.iterator();
    }

    public boolean lessThen30(){
        Calendar now = Calendar.getInstance();
        Calendar start = timeAvailable.getCalendarStart();
        long minutes = ChronoUnit.MINUTES.between(now.toInstant(), start.toInstant());
        return minutes <= 30;
    }
}
