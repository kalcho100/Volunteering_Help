package ucl.ac.uk.main;

import java.time.temporal.ChronoUnit;
import java.util.*;

public class Model {
    private final DataManager dataManager;
    private final Coordinates coordinates;
    private User user;

    public Model(){
        dataManager = Main.getDataManager();
        coordinates = new Coordinates();
        user = null;
    }

    public int login(String username, String password){
        user = dataManager.loginSearch(username, password);
        if(user == null){
            return 0;
        }
        else if(user instanceof Volunteer){
            return 1;
        }
        else if(user instanceof Vulnerable){
            return 2;
        }
        else if(user instanceof Shop){
            return 3;
        }
        else{
            return 4;
        }
    }

    public User getUser(){
        return this.user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void addGovernment(Government aGovernment){
        dataManager.addGovernment(aGovernment.getCity() + ", " + aGovernment.getCountry(), aGovernment);
    }

    public void removeGovernment(Government government){
        dataManager.removeGovernment(government);
    }

    public void addShop(Shop aShop){
        dataManager.addShop(aShop.getCity() + ", " + aShop.getCountry(), aShop);
    }

    public void removeShop(Shop shop){
        dataManager.removeShop(shop);
    }

    public void addVulnerable(Vulnerable vulnerable){
        dataManager.addVulnerable(vulnerable.getCity() + ", " + vulnerable.getCountry(), vulnerable);
    }

    public void removeVulnerable(Vulnerable vulnerable){
        dataManager.removeVulnerable(vulnerable);
    }

    public void addVolunteer(Volunteer volunteer){
        dataManager.addVolunteer(volunteer);
    }

    public void removeVolunteer(Volunteer volunteer){
        dataManager.removeVolunteer(volunteer);
    }

    public void addArea(Volunteer volunteer, String address, String city, String country, int radius, String startDate, String startTime, String finishDate, String finishTime){
        Area area = new Area(address, city, country, radius);
        area.setStartTimeDate(startTime, startDate);
        area.setFinishTimeDate(finishTime, finishDate);
        area.setAreaOf(volunteer);
        volunteer.addArea(area);
        dataManager.addArea(area);
    }

    public void removeArea(Volunteer volunteer, Area area){
        dataManager.removeArea(area);
        volunteer.removeArea(area);
    }

    public void saveVolunteers(){
        dataManager.saveVolunteers();
    }

    public void saveVulnerables(){
        dataManager.saveVulnerables();
    }

    public void saveGovernments(){
        dataManager.saveGovernments();
    }

    public void saveShops(){
        dataManager.saveShops();
    }

    public boolean isVulnerableRegistered(Vulnerable vulnerable){
        return dataManager.isVulnerableRegistered(vulnerable);
    }

    public void removeRegisteredVulnerable(Government government, int index){
        government.removeVulnerable(index);
    }

    private ArrayList<Request> listRequestsInArea(double lat, double lng, int radius, String city, String country){
        String city1 = city + ", " + country;
        ArrayList<User> vulnerables = dataManager.listVulnerablesInArea(lat, lng, radius, city1);
        ArrayList<Request> result = new ArrayList<>();
        for(User a : vulnerables){
            Vulnerable vulnerable = (Vulnerable) a;
            Iterator<Request> iterator = vulnerable.getRequestsIterator();
            while (iterator.hasNext()){
                Request request = iterator.next();
                if(!request.getIsAccepted()) {
                    result.add(request);
                }
            }
        }
        return result;
    }

    private ArrayList<Shop> listShopsInArea(Area area){
        double lat = area.getLat();
        double lng = area.getLng();
        int radius = area.getRadius();
        String city1 = area.getCity() + ", " + area.getCountry();
        ArrayList<User> shops = dataManager.listShopsInArea(lat, lng, radius, city1);
        ArrayList<Shop> result = new ArrayList<>();
        for(User a : shops){
            result.add((Shop) a);
        }
        return result;
    }

    private double distance(Area area, User user){
        return coordinates.distance(area.getLat(), area.getLng(), user.getLat(), user.getLng());
    }

    private double distance(User aUser, User user){
        return coordinates.distance(aUser.getLat(), aUser.getLng(), user.getLat(), user.getLng());
    }

    private double minutesToWalkAndWait(double km){
        return km * 12 + 2 + 5;
    }

    private int numberOfOtherAvailableVolunteers(Request request){
        Vulnerable vulnerable = request.getRequestedBy();
        String product = request.getProduct();
        int quantity = request.getQuantity();
        ArrayList<Area> areas = dataManager.listSuitableAreas(vulnerable.getCity(), vulnerable.getCountry(), vulnerable.getLat(), vulnerable.getLng());
        ArrayList<Area> dummy = new ArrayList<>();
        for(Area area : areas){
            ArrayList<Shop> shops = listShopsInArea(area);
            for(Shop shop : shops){
                try {
                    if (shop.getQuantity(product) >= quantity) {
                        if (minutesToWalkAndWait(distance(area, shop)) + minutesToWalkAndWait(distance(shop, request.getRequestedBy())) <= area.getMinutesAvailable()) {
                            dummy.add(area);
                            break;
                        }
                    }
                }catch (NullPointerException e){
                    continue;
                }
            }
        }
        return dummy.size();
    }

    public ArrayList<Object> listSuitableRequests(Area area){
        RequestList requestList = createQueue(area);
        ArrayList<Shop> shops = listShopsInArea(area);
        Graph graph = new Graph(area, shops, requestList);
        return graph.bestRoute();
    }

   private double calculateScore(Request request){
        int volunteers = numberOfOtherAvailableVolunteers(request);
        double type;
        if(request.getType().equals("pharmacy")){
            type = -0.5;
        }
        else{
            type = 0.5;
        }
        long days = ChronoUnit.DAYS.between(Calendar.getInstance().toInstant(), request.getDeliverBy().toInstant()) + 1;
        return volunteers + type + (days / 2);
   }

   private RequestList createQueue(Area area){
        ArrayList<Request> requests = listRequestsInArea(area.getLat(), area.getLng(), area.getRadius(), area.getCity(), area.getCountry());
        RequestList list = new RequestList();
        for(Request request : requests){
            list.add(calculateScore(request), request);
        }
        return list;
   }


}
