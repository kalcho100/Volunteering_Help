package ucl.ac.uk.main;

import java.util.ArrayList;
import java.util.Iterator;

public class Container {
    private ArrayList<User> areas;
    private Coordinates coordinates;
    private double lat;
    private double lng;
    private Container left_left;
    private Container left;
    private Container right;
    private Container right_right;

    public Container(User user){
        this.areas = new ArrayList<>();
        this.coordinates = new Coordinates();
        this.areas.add(user);
        this.lat = user.getLat();
        this.lng = user.getLng();
        this.left_left = null;
        this.left = null;
        this.right = null;
        this.right_right = null;
    }

    public Container(double lat, double lng){
        this.areas = new ArrayList<>();
        this.coordinates = new Coordinates();
        this.lat = lat;
        this.lng = lng;
        this.left_left = null;
        this.left = null;
        this.right = null;
        this.right_right = null;
    }

    public Container(String city){
        this.areas = new ArrayList<>();
        this.coordinates = new Coordinates();
        try {
            ArrayList<Double> result = coordinates.calculateCoords(city);
            this.lat = result.get(0);
            this.lng = result.get(1);
        }catch (Exception e){
            System.out.println("Calculating initial coordinates for a container failed.  " + city);
        }
        this.left_left = null;
        this.left = null;
        this.right = null;
        this.right_right = null;
    }

    public ArrayList<User> getUsers(){
        return this.areas;
    }

    public double getLat(){
        return this.lat;
    }

    public double getLng(){
        return this.lng;
    }

    public Container getLeft_left(){
        return this.left_left;
    }

    public Container getLeft(){
        return this.left;
    }

    public Container getRight(){
        return this.right;
    }

    public Container getRight_right(){
        return this.right_right;
    }

    public void setLeft_left(User user){
        this.left_left = new Container(user);
    }

    public void setLeft(User user){
        this.left = new Container(user);
    }

    public void setRight(User user){
        this.right = new Container(user);
    }

    public void setRight_right(User user){
        this.right_right = new Container(user);
    }

    public int size(){
        int result = 0;
        result += areas.size();
        if(left_left != null){
            result += left_left.size();
        }
        if(left != null){
            result += left.size();
        }
        if(right != null){
            result += right.size();
        }
        if(right_right != null){
            result += right_right.size();
        }
        return result;
    }

    public Container copy(){
        Container result = new Container(getLat(), getLng());
        result.areas.addAll(areas);
        if(left_left != null) {
            result.left_left = getLeft_left().copy();
        }
        if(left != null) {
            result.left = getLeft().copy();
        }
        if(right != null) {
            result.right = getRight().copy();
        }
        if(right_right != null) {
            result.right_right = getRight_right().copy();
        }
        return  result;
    }

    public void add(User aUser){
        if(aUser.getLat() == this.getLat() && aUser.getLng() == this.getLng()){
            areas.add(aUser);
        }
        else if(aUser.getLat() < this.getLat() && aUser.getLng() < this.getLng()){
            if(left_left == null){
                setLeft_left(aUser);
            }
            else{
                left_left.add(aUser);
            }
        }
        else if(aUser.getLat() < this.getLat() && aUser.getLng() >= this.getLng()){
            if(left == null){
                setLeft(aUser);
            }
            else{
                left.add(aUser);
            }
        }
        else if(aUser.getLat() >= this.getLat() && aUser.getLng() < this.getLng()){
            if(right == null){
                setRight(aUser);
            }
            else{
                right.add(aUser);
            }
        }
        else{
            if(right_right == null){
                setRight_right(aUser);
            }
            else{
                right_right.add(aUser);
            }
        }
    }

    public ArrayList<User> listSuitable(double lat, double lng, int radius){
        ArrayList<Double> points = coordinates.endPoints(lat, lng, radius);
        double latMax = points.get(0);
        double latMin = points.get(1);
        double lngMax = points.get(2);
        double lngMin = points.get(3);
        ArrayList<User> result = new ArrayList<>();
        if(this.getLat() < latMax && this.getLat() > latMin){
            if(this.getLng() < lngMax && this.getLng() > lngMin){
                for(User a : areas){
                    if(coordinates.distance(lat, lng, a.getLat(), a.getLng()) <= radius){
                        result.add(a);
                    }
                }
                if(left_left != null) {
                    result.addAll(left_left.listSuitable(lat, lng, radius));
                }
                if(left != null) {
                    result.addAll(left.listSuitable(lat, lng, radius));
                }
                if(right_right != null) {
                    result.addAll(right_right.listSuitable(lat, lng, radius));
                }
                if(right != null) {
                    result.addAll(right.listSuitable(lat, lng, radius));
                }
            }
            else if(this.getLng() <= lngMin || this.getLng() == lngMax){
                if(this.getLng() == lngMax || this.getLng() == lngMin){
                    for(User a : areas){
                        if(coordinates.distance(lat, lng, a.getLat(), a.getLng()) <= radius){
                            result.add(a);
                        }
                    }
                }
                if(left != null) {
                    result.addAll(left.listSuitable(lat, lng, radius));
                }
                if(right_right != null) {
                    result.addAll(right_right.listSuitable(lat, lng, radius));
                }
            }
            else{
                if(left_left != null) {
                    result.addAll(left_left.listSuitable(lat, lng, radius));
                }
                if(right != null) {
                    result.addAll(right.listSuitable(lat, lng, radius));
                }
            }
        }
        else if(this.getLat() > latMax){
            if(this.getLng() < lngMax && this.getLng() > lngMin){
                if(left_left != null) {
                    result.addAll(left_left.listSuitable(lat, lng, radius));
                }
                if(left != null) {
                    result.addAll(left.listSuitable(lat, lng, radius));
                }
            }
            else if(this.getLng() <= lngMin || this.getLng() == lngMax){
                if(left != null) {
                    result.addAll(left.listSuitable(lat, lng, radius));
                }
            }
            else{
                if(left_left != null) {
                    result.addAll(left_left.listSuitable(lat, lng, radius));
                }
            }
        }
        else if(this.getLat() <= latMin || this.getLat() == latMax){
            if(this.getLng() < lngMax && this.getLng() > lngMin){
                if(this.getLat() == latMax || this.getLat() == latMin){
                    for(User a : areas){
                        if(coordinates.distance(lat, lng, a.getLat(), a.getLng()) <= radius){
                            result.add(a);
                        }
                    }
                }
                if(right != null) {
                    result.addAll(right.listSuitable(lat, lng, radius));
                }
                if(right_right != null) {
                    result.addAll(right_right.listSuitable(lat, lng, radius));
                }
            }
            else if(this.getLng() <= lngMin || this.getLng() == lngMax){
                if(right_right != null) {
                    result.addAll(right_right.listSuitable(lat, lng, radius));
                }
            }
            else{
                if(right != null) {
                    result.addAll(right.listSuitable(lat, lng, radius));
                }
            }
        }
        return result;
    }

    public void remove(User aUser){
        if(aUser.getLat() == this.getLat() && aUser.getLng() == this.getLng()){
            areas.remove(aUser);
        }
        else if(aUser.getLat() < this.getLat() && aUser.getLng() < this.getLng()){
            if(left_left != null){
                left_left.remove(aUser);
            }
        }
        else if(aUser.getLat() < this.getLat() && aUser.getLng() >= this.getLng()){
            if(left != null) {
                left.remove(aUser);
            }
        }
        else if(aUser.getLat() >= this.getLat() && aUser.getLng() < this.getLng()){
            if(right != null){
                right.remove(aUser);
            }
        }
        else{
            if(right_right != null){
                right_right.remove(aUser);
            }
        }
    }

    private class ContainerIterator implements Iterator<User> {
        Container current;
        int size;
        int i;

        public ContainerIterator(boolean first, Container container){
            this.size = size();
            this.i = 0;
            if(first) {
                current = container.copy();
            }
            else{
                current = container;
            }
        }
        @Override
        public boolean hasNext() {
            return i < size;
        }

        @Override
        public User next() {
            if(!current.areas.isEmpty()){
                User user = current.areas.get(i);
                current.areas.remove(user);
                i++;
                return user;
            }
            else{
                if(current.left_left != null){
                    Iterator<User> iterator = current.left_left.iterators();
                    User user = iterator.next();
                    if(user != null){
                        i++;
                        return user;
                    }
                }
                if(current.left != null){
                    Iterator<User> iterator = current.left.iterators();
                    User user = iterator.next();
                    if(user != null){
                        i++;
                        return user;
                    }
                }
                if(current.right != null){
                    Iterator<User> iterator = current.right.iterators();
                    User user = iterator.next();
                    if(user != null){
                        i++;
                        return user;
                    }
                }
                if(current.right_right != null){
                    Iterator<User> iterator = current.right_right.iterators();
                    User user = iterator.next();
                    if(user != null){
                        i++;
                        return user;
                    }
                }
            }
            return  null;
        }
    }

    public Iterator<User> iterator(){
        return new ContainerIterator(true, this);
    }

    private Iterator<User> iterators(){
        return new ContainerIterator(false, this);
    }
}
