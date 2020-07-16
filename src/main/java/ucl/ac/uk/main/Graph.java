package ucl.ac.uk.main;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class Graph {
    private final Area area;
    private ArrayList<ArrayList<Double>> graph;
    private ArrayList<Object> nodes;
    private ArrayList<Integer> indexes;
    private final Coordinates coordinates;
    private final int size;
    private final int shopsSize;
    private double distance;
    private int numRequests;
    private final double minutesAvailable;
    private Calendar startTime;

    public Graph(Area area, ArrayList<Shop> shops, RequestList requests){
        this.area = area;
        coordinates = new Coordinates();
        graph = new ArrayList<>();
        nodes = new ArrayList<>();
        indexes = new ArrayList<>();
        nodes.add(area);
        nodes.addAll(shops);
        Iterator<Request> iterator = requests.iterator();
        while(iterator.hasNext()){
            nodes.add(iterator.next());
        }
        initialiseGraph(area, shops, requests);
        this.size = nodes.size();
        this.shopsSize = shops.size();
        this.distance = 100000000;
        this.numRequests = 0;
        this.minutesAvailable = area.getMinutesAvailable();
        this.startTime = area.getCalendarStart();
    }

    public void initialiseGraph(Area area, ArrayList<Shop> shops, RequestList requests){
        ArrayList<Double> dummy = new ArrayList<>();
        double lat = area.getLat();
        double lng = area.getLng();
        double lat1;
        double lng1;
        dummy.add(0.0);
        for(Shop shop : shops) {
            lat1 = shop.getLat();
            lng1 = shop.getLng();
            dummy.add(coordinates.distance(lat, lng, lat1, lng1));
        }
        Iterator<Request> iterator = requests.iterator();
        while(iterator.hasNext()){
            Vulnerable vulnerable = iterator.next().getRequestedBy();
            lat1 = vulnerable.getLat();
            lng1 = vulnerable.getLng();
            dummy.add(coordinates.distance(lat, lng, lat1, lng1));
        }
        graph.add(dummy);
        dummy = new ArrayList<>();
        for(Shop shop : shops){
            lat = shop.getLat();
            lng = shop.getLng();
            dummy.add(coordinates.distance(lat, lng, area.getLat(), area.getLng()));
            for(Shop shop1 : shops){
                lat1 = shop1.getLat();
                lng1 = shop1.getLng();
                dummy.add(coordinates.distance(lat, lng, lat1, lng1));
            }
            iterator = requests.iterator();
            while(iterator.hasNext()){
                Vulnerable vulnerable = iterator.next().getRequestedBy();
                lat1 = vulnerable.getLat();
                lng1 = vulnerable.getLng();
                dummy.add(coordinates.distance(lat, lng, lat1, lng1));
            }
            graph.add(dummy);
            dummy = new ArrayList<>();
        }
        iterator = requests.iterator();
        while(iterator.hasNext()){
        Vulnerable request = iterator.next().getRequestedBy();
            lat = request.getLat();
            lng = request.getLng();
            dummy.add(coordinates.distance(lat, lng, area.getLat(), area.getLng()));
            for(Shop shop1 : shops){
                lat1 = shop1.getLat();
                lng1 = shop1.getLng();
                dummy.add(coordinates.distance(lat, lng, lat1, lng1));
            }
            Iterator<Request> iterator1 = requests.iterator();
            while(iterator1.hasNext()){
                Vulnerable vulnerable = iterator1.next().getRequestedBy();
                lat1 = vulnerable.getLat();
                lng1 = vulnerable.getLng();
                dummy.add(coordinates.distance(lat, lng, lat1, lng1));
            }
            graph.add(dummy);
            dummy = new ArrayList<>();
        }
    }

    private String dayToDay(int day){
        switch (day){
            case 1: return "Sunday";
            case 2: return "Monday";
            case 3: return "Tuesday";
            case 4: return "Wednesday";
            case 5: return "Thursday";
            case 6: return "Friday";
            case 7: return "Saturday";
            default: return "nz";
        }
    }

    private double minutesToWalkAndWait(double km){
        return km * 12 + 2 + 5;
    }

    private double arithmeticSum(int n){
        return (n - 1) * n / 2;
    }

    private void bestRoute(int x, ArrayList<Object> nodes, ArrayList<Integer> visited, double distance, int numRequests, double sumIndexRequests, HashMap<String, Integer> products){
        if(x >= size){
            return;
        }
        boolean used = false;
        Object node = nodes.get(x);
        visited.add(x);
        if(node instanceof Shop){
            Shop shop = (Shop) node;
            Calendar time = (Calendar) startTime.clone();
            time.add(Calendar.MINUTE, (int) minutesToWalkAndWait(distance));
            if(!shop.isOpen(time.get(Calendar.DAY_OF_WEEK), time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE))){
                visited.remove(visited.size() - 1);
                return;
            }
            Iterator<String> iterator = shop.getProductsIterator();
            while(iterator.hasNext()){
                String product = iterator.next();
                if(products.get(product) == null){
                    products.put(product, shop.getQuantity(product));
                }
                else{
                    products.put(product, products.get(product) + shop.getQuantity(product));
                }
            }
        }
        if(node instanceof Request) {
            Request request = (Request) node;
            String product = request.getProduct();
            sumIndexRequests += x - shopsSize - 1;
            if (products.get(product) != null && products.get(product) >= request.getQuantity()) {
                numRequests++;
                if (sumIndexRequests == arithmeticSum(numRequests)) {
                    products.put(product, products.get(product) - request.getQuantity());
                    used = true;
                    if (numRequests > this.numRequests) {
                        this.numRequests = numRequests;
                        this.distance = distance;
                        indexes = new ArrayList<>();
                        indexes.addAll(visited);
                    } else if (numRequests == this.numRequests) {
                        if (distance < this.distance) {
                            this.distance = distance;
                            indexes = new ArrayList<>();
                            indexes.addAll(visited);
                        }
                    }
                }
                else {
                    visited.remove(visited.size() - 1);
                    return;
                }
            }
            else {
                visited.remove(visited.size() - 1);
                return;
            }
        }
        int i = x;
        while(i > 0){
            if(!visited.contains(i - 1) && minutesToWalkAndWait(distance + graph.get(i - 1).get(x)) <= minutesAvailable) {
                bestRoute(i - 1, nodes, visited, distance + graph.get(i - 1).get(x), numRequests, sumIndexRequests, products);
            }
            i--;
        }
        i = x;
        while (i < size - 1 ){
            if(!visited.contains(i + 1) && minutesToWalkAndWait(distance + graph.get(i + 1).get(x)) <= minutesAvailable) {
                bestRoute(i + 1, nodes, visited, distance + graph.get(i + 1).get(x), numRequests, sumIndexRequests, products);
            }
            i++;
        }
        if(node instanceof  Shop){
            Shop shop = (Shop) node;
            Iterator<String> iterator = shop.getProductsIterator();
            while (iterator.hasNext()){
                String product = iterator.next();
                products.put(product, products.get(product) - shop.getQuantity(product));
            }
        }
        if(used){
            Request request = (Request) node;
            String product = request.getProduct();
            int quantity = request.getQuantity();
            products.merge(product, quantity, Integer::sum);
        }
        visited.remove(visited.size() - 1);
    }

    public ArrayList<Object> bestRoute(){
        ArrayList<Object> result = new ArrayList<>();
        bestRoute(0, nodes, new ArrayList<>(), 0, 0, 0, new HashMap<>());
        for(int i : indexes){
            result.add(nodes.get(i));
        }
        return result;
    }

    public static void main(String[] args) {
        Calendar start = Calendar.getInstance();
        Area area = new Area("kv. Borovo", "Sofia", "Bulgaria", 10);
        area.setStartTimeDate("09:00", "2020-06-28");
        area.setFinishTimeDate("18:00", "2020-06-28");
        Shop shop = new Shop("shop", "shop");
        shop.registerAddress("ul. \"Ladoga\" 3, 1612 g.k. Krasno selo, Sofia, Bulgaria");
        shop.addNewProduct("a", 2);
        shop.addNewProduct("bread", 2);
        Shop shop1 = new Shop("shop", "shop");
        shop1.registerAddress("Buxton Brothers Boulevard 22, 1618 Bakston, Sofia, Bulgaria");
        shop1.addNewProduct("b", 2);
        shop1.addNewProduct("milk", 3);
        Shop shop2 = new Shop("shop", "shop");
        shop2.registerAddress("ul. \"Todor Kableshkov\" 3, 1618 Bakston, Sofia, Bulgaria");
        shop2.addNewProduct("bread", 5);
        shop2.addNewProduct("d", 6);
        ArrayList<Shop> shops = new ArrayList<>();
        shops.add(shop);
        shops.add(shop1);
        shops.add(shop2);
        Vulnerable vulnerable = new Vulnerable("vul", "vuls");
        vulnerable.registerAddress("ul. \"Mayor Gortalov\" 15, 1618 Bakston, Sofia, Bulgaria");
        Vulnerable vulnerable1 = new Vulnerable("vol", "vols");
        vulnerable1.setLat(42.671803);
        vulnerable1.setLng(23.275989);
        Request request = new Request(1, "bread", 2, "shop");
        request.setRequestedBy(vulnerable);
        Request request1 = new Request(2, "milk", 2, "shop");
        request1.setRequestedBy(vulnerable1);
        Request request2 = new Request(5, "da", 2, "shop");
        request2.setRequestedBy(vulnerable);
        RequestList requestList = new RequestList();
        requestList.add(0, request);
        requestList.add(1, request1);
        requestList.add(2, request2);
        Calendar mid = Calendar.getInstance();
        Graph graph = new Graph(area, shops, requestList);
        System.out.println(shop);
        System.out.println(shop1);
        System.out.println(shop2);
        System.out.println(request);
        System.out.println(request1);
        System.out.println(request2);
        System.out.println(graph.bestRoute());
        Calendar finish = Calendar.getInstance();
        System.out.println(ChronoUnit.MICROS.between(start.toInstant(), mid.toInstant()));
        System.out.println(ChronoUnit.MICROS.between(mid.toInstant(), finish.toInstant()));
    }
}
