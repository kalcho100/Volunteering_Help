package ucl.ac.uk.main;

import java.util.ArrayList;
import java.util.Iterator;

public class AreaContainer {
    private ArrayList<Area> areas;
    private Coordinates coordinates;
    private double lat;
    private double lng;
    private AreaContainer left_left;
    private AreaContainer left;
    private AreaContainer right;
    private AreaContainer right_right;

    public AreaContainer(Area area){
        this.areas = new ArrayList<>();
        this.coordinates = new Coordinates();
        this.areas.add(area);
        this.lat = area.getLat();
        this.lng = area.getLng();
        this.left_left = null;
        this.left = null;
        this.right = null;
        this.right_right = null;
    }

    public AreaContainer(double lat, double lng){
        this.areas = new ArrayList<>();
        this.coordinates = new Coordinates();
        this.lat = lat;
        this.lng = lng;
        this.left_left = null;
        this.left = null;
        this.right = null;
        this.right_right = null;
    }

    public AreaContainer(String city){
        this.areas = new ArrayList<>();
        this.coordinates = new Coordinates();
        try {
            ArrayList<Double> result = coordinates.calculateCoords(city);
            this.lat = result.get(0);
            this.lng = result.get(1);
        }catch (Exception e){
            System.out.println("Calculating initial coordinates for an areaContainer failed.  " + city);
        }
        this.left_left = null;
        this.left = null;
        this.right = null;
        this.right_right = null;
    }

    public ArrayList<Area> getAreas(){
        return this.areas;
    }

    public double getLat(){
        return this.lat;
    }

    public double getLng(){
        return this.lng;
    }

    public AreaContainer getLeft_left(){
        return this.left_left;
    }

    public AreaContainer getLeft(){
        return this.left;
    }

    public AreaContainer getRight(){
        return this.right;
    }

    public AreaContainer getRight_right(){
        return this.right_right;
    }

    public void setLeft_left(Area area){
        this.left_left = new AreaContainer(area);
    }

    public void setLeft(Area area){
        this.left = new AreaContainer(area);
    }

    public void setRight(Area area){
        this.right = new AreaContainer(area);
    }

    public void setRight_right(Area area){
        this.right_right = new AreaContainer(area);
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

    public AreaContainer copy(){
        AreaContainer result = new AreaContainer(getLat(), getLng());
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

    public void add(Area area){
        if(area.getLat() == this.getLat() && area.getLng() == this.getLng()){
            areas.add(area);
        }
        else if(area.getLat() < this.getLat() && area.getLng() < this.getLng()){
            if(left_left == null){
                setLeft_left(area);
            }
            else{
                left_left.add(area);
            }
        }
        else if(area.getLat() < this.getLat() && area.getLng() >= this.getLng()){
            if(left == null){
                setLeft(area);
            }
            else{
                left.add(area);
            }
        }
        else if(area.getLat() >= this.getLat() && area.getLng() < this.getLng()){
            if(right == null){
                setRight(area);
            }
            else{
                right.add(area);
            }
        }
        else{
            if(right_right == null){
                setRight_right(area);
            }
            else{
                right_right.add(area);
            }
        }
    }

    public ArrayList<Area> listSuitable(double lat, double lng, int radius){
        ArrayList<Double> points = coordinates.endPoints(lat, lng, radius);
        double latMax = points.get(0);
        double latMin = points.get(1);
        double lngMax = points.get(2);
        double lngMin = points.get(3);
        ArrayList<Area> result = new ArrayList<>();
        if(this.getLat() < latMax && this.getLat() > latMin){
            if(this.getLng() < lngMax && this.getLng() > lngMin){
                for(Area a : areas){
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
                    for(Area a : areas){
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
                    for(Area a : areas){
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

    public void remove(Area area){
        if(area.getLat() == this.getLat() && area.getLng() == this.getLng()){
            areas.remove(area);
        }
        else if(area.getLat() < this.getLat() && area.getLng() < this.getLng()){
            if(left_left != null){
                left_left.remove(area);
            }
        }
        else if(area.getLat() < this.getLat() && area.getLng() >= this.getLng()){
            if(left != null) {
                left.remove(area);
            }
        }
        else if(area.getLat() >= this.getLat() && area.getLng() < this.getLng()){
            if(right != null){
                right.remove(area);
            }
        }
        else{
            if(right_right != null){
                right_right.remove(area);
            }
        }
    }

    private class ContainerIterator implements Iterator<Area> {
        AreaContainer current;
        int size;
        int i;

        public ContainerIterator(boolean first, AreaContainer container){
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
        public Area next() {
            if(!current.areas.isEmpty()){
                Area area = current.areas.get(i);
                current.areas.remove(area);
                i++;
                return area;
            }
            else{
                if(current.left_left != null){
                    Iterator<Area> iterator = current.left_left.iterators();
                    Area area = iterator.next();
                    if(area != null){
                        i++;
                        return area;
                    }
                }
                if(current.left != null){
                    Iterator<Area> iterator = current.left.iterators();
                    Area area = iterator.next();
                    if(area != null){
                        i++;
                        return area;
                    }
                }
                if(current.right != null){
                    Iterator<Area> iterator = current.right.iterators();
                    Area area = iterator.next();
                    if(area != null){
                        i++;
                        return area;
                    }
                }
                if(current.right_right != null){
                    Iterator<Area> iterator = current.right_right.iterators();
                    Area area = iterator.next();
                    if(area != null){
                        i++;
                        return area;
                    }
                }
            }
            return  null;
        }
    }

    public Iterator<Area> iterator(){
        return new ContainerIterator(true, this);
    }

    private Iterator<Area> iterators(){
        return new ContainerIterator(false, this);
    }
}
