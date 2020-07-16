package ucl.ac.uk.main;

import java.util.ArrayList;
import java.util.Iterator;

public class Volunteer extends User {
    private String firstName;
    private String lastName;
    private ArrayList<Area> areas;

    public Volunteer(String username, String password){
        super(username, password);
        areas = new ArrayList<>();
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public ArrayList<Area> getAreas(){
        return areas;
    }

    public void addArea(String address, String city, String country, int radius, String startDate, String startTime, String finishDate, String finishTime){
        Area area = new Area(address, city, country, radius);
        area.setStartTimeDate(startTime, startDate);
        area.setFinishTimeDate(finishTime, finishDate);
        area.setAreaOf(this);
        areas.add(area);
    }

    public void addArea(Area area){
        areas.add(area);
    }

    public void removeArea(int index){
        areas.remove(index);
    }

    public void removeArea(Area area){
        areas.remove(area);
    }

    public void setStartTime(int index, String date, String time){
        areas.get(index).setStartTimeDate(time, date);
    }

    public void setFinishTime(int index, String date, String time){
        areas.get(index).setFinishTimeDate(time, date);
    }

    public void addRequest(Area area, Request request){
        for(Area a : areas){
            if(a.equals(area)){
                request.setAccepted(this);
                a.addRequest(request);
            }
        }
    }

    public void removeRequest(Area area, Request request){
        for(Area a : areas){
            if(a.equals(area)){
                a.removeRequest(request);
            }
        }
    }

    public Iterator<Area> getAreasIterator(){
        return areas.iterator();
    }

    public Iterator<Request> getRequestsIterator(Area area){
        return area.getRequestsIterator();
    }


    public boolean equals(String first, String last, String address, String city, String country){
        if(!this.getFirstName().equals(first)){
            return false;
        }
        if(!this.getLastName().equals(last)){
            return false;
        }
        if(!this.getAddress().equals(address)){
            return false;
        }
        if(!this.getCity().equals(city)){
            return false;
        }
        if(!this.getCountry().equals(country)){
            return false;
        }
        return true;
    }
}
