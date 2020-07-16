package ucl.ac.uk.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class DataManager {
    private HashMap<String, Container> volunteers;
    private HashMap<String, HashMap<Integer, AreaContainer>> areas;
    private HashMap<String, Container> vulnerables;
    private HashMap<String, Container> shops;
    private HashMap<String, Government> governments;

    public DataManager(){
        volunteers = new HashMap<>();
        areas = new HashMap<>();
        vulnerables = new HashMap<>();
        shops = new HashMap<>();
        governments = new HashMap<>();
        loadGovernments();
        loadShops();
//        loadVulnerables();
//        loadData();
    }

    public boolean isVulnerableRegistered(Vulnerable vulnerable){
        Government government = governments.get(vulnerable.getCity() + ", " + vulnerable.getCountry());
        if(government == null){
            return false;
        }
        return government.isVulnerableRegistered(vulnerable);
    }

    public void addVolunteer(Volunteer a){
        String city = a.getCity() + ", " + a.getCountry();
        if(volunteers.containsKey(city)){
            volunteers.get(city).add(a);
        }
        else{
            Container dummy = new Container(city);
            dummy.add(a);
            volunteers.put(city, dummy);
        }
    }

    public void addArea(Area area){
        int radius = area.getRadius();
        if(areas.containsKey(area.getCity() + ", " + area.getCountry())){
            HashMap<Integer, AreaContainer> hashMap = areas.get(area.getCity() + ", " + area.getCountry());
            if(hashMap.containsKey(radius)){
                hashMap.get(radius).add(area);
            }
            else{
                hashMap.put(radius, new AreaContainer(area));
            }
        }
        else{
            HashMap<Integer, AreaContainer> hashMap = new HashMap<>();
            AreaContainer areaContainer = new AreaContainer(area);
            hashMap.put(radius, areaContainer);
            areas.put(area.getCity() + ", " + area.getCountry(), hashMap);
        }
    }

    public void addVulnerable(String city, Vulnerable a){
        if(vulnerables.containsKey(city)){
            vulnerables.get(city).add(a);
        }
        else{
            Container dummy = new Container(city);
            dummy.add(a);
            vulnerables.put(city, dummy);
        }
    }

    public void addShop(String city, Shop a){
        if(shops.containsKey(city)){
            shops.get(city).add(a);
        }
        else{
            Container dummy = new Container(city);
            dummy.add(a);
            shops.put(city, dummy);
        }
    }

    public void addGovernment(String city, Government a){
        if(!governments.containsKey(city)){
            governments.put(city, a);
        }
    }

    public void addUser(String city, User aUser){
        if(aUser instanceof Volunteer){
            addVolunteer((Volunteer) aUser);
        }
        else if(aUser instanceof Vulnerable){
            addVulnerable(city, (Vulnerable) aUser);
        }
        else if(aUser instanceof Shop){
            addShop(city, (Shop) aUser);
        }
        else if(aUser instanceof Government){
            addGovernment(city, (Government) aUser);
        }
    }

    public void removeVolunteer(Volunteer a) {
        volunteers.get(a.getCity() + ", " + a.getCountry()).remove(a);
    }

    public void removeArea(Area area){
        if(areas.containsKey(area.getCity() + ", " + area.getCountry())) {
            if(areas.get(area.getCity() + ", " + area.getCountry()).containsKey(area.getRadius())) {
                areas.get(area.getCity() + ", " + area.getCountry()).get(area.getRadius()).remove(area);
            }
        }
    }

    public void removeVulnerable(Vulnerable a){
        vulnerables.get(a.getCity() + ", " + a.getCountry()).remove(a);
    }

    public void removeShop(Shop a){
        shops.get(a.getCity() + ", " + a.getCountry()).remove(a);
    }

    public void removeGovernment(Government a){
        governments.remove(a.getCity() + ", " + a.getCountry());
    }

    private Volunteer searchVolunteer(String first, String last, String address, String city, String country){
        Iterator<Container> iterator = volunteers.values().iterator();
        while(iterator.hasNext()) {
            Container b = iterator.next();
            Iterator<User> iterators = b.iterator();
            while (iterators.hasNext()){
                Volunteer a = (Volunteer) iterators.next();
                if (a.equals(first, last, address, city, country)) {
                    return a;
                }
            }
        }
        return null;
    }

    public User loginSearch(String username, String password){
        Iterator<Container> iterator = volunteers.values().iterator();
        while(iterator.hasNext()) {
            Container a = iterator.next();
            Iterator<User> iterators = a.iterator();
            while (iterators.hasNext()){
                Volunteer b = (Volunteer) iterators.next();
                if(b.getUsername().equals(username)){
                    if(b.getPassword().equals(password)){
                        return b;
                    }
                }
            }
        }
        iterator = vulnerables.values().iterator();
        while(iterator.hasNext()) {
            Container a = iterator.next();
            Iterator<User> iterators = a.iterator();
            while (iterators.hasNext()) {
                User b = iterators.next();
                if (b.getUsername().equals(username)) {
                    if (b.getPassword().equals(password)) {
                        return b;
                    }
                }
            }
        }
        iterator = shops.values().iterator();
        while(iterator.hasNext()) {
            Container a = iterator.next();
            Iterator<User> iterators = a.iterator();
            while (iterators.hasNext()) {
                User b = iterators.next();
                if (b.getUsername().equals(username)) {
                    if (b.getPassword().equals(password)) {
                        return b;
                    }
                }
            }
        }
        for(Government a : governments.values()){
            if(a.getUsername().equals(username)){
                if(a.getPassword().equals(password)){
                    return a;
                }
            }
        }
        return null;
    }

    private Iterator<Integer> getRadiusIterator(String city, String country){
        return areas.get(city + ", " + country).keySet().iterator();
    }

    public ArrayList<Area> listSuitableAreas(String city, String country, double lat, double lng){
        ArrayList<Area> result = new ArrayList<>();
        HashMap<Integer, AreaContainer> hashMap = areas.get(city + ", " + country);
        Iterator<Integer> iterator = getRadiusIterator(city, country);
        while(iterator.hasNext()){
            int radius = iterator.next();
            result.addAll(hashMap.get(radius).listSuitable(lat, lng, radius));
        }
        return result;
    }

    private void saveUser(FileWriter file, User user) throws IOException{
        StringBuilder string = new StringBuilder();
        string.append(user.getUsername()).append(";");
        string.append(user.getPassword()).append(";");
        string.append(user.getAddress()).append(";");
        string.append(user.getCity()).append(";");
        string.append(user.getCountry()).append(";");
        string.append(user.getLat()).append(";");
        string.append(user.getLng());
        file.write(string.toString());
    }

    private void saveVolunteer(FileWriter file, Volunteer a) throws IOException {
        StringBuilder string = new StringBuilder();
        saveUser(file, a);
        string.append(";");
        string.append(a.getFirstName()).append(";");
        string.append(a.getLastName()).append("\n");
        file.write(string.toString());
        string =  new StringBuilder();
        boolean first = true;
        Iterator<Area> iterator = a.getAreasIterator();
        while(iterator.hasNext()){
            Area area = iterator.next();
            if(first) {
                string.append(area.getAddress()).append(";").append(area.getCity()).append(";").append(area.getCountry()).append(";");
                first = false;
            }
            else{
                string.append(";").append(area.getAddress()).append(";").append(area.getCity()).append(";").append(area.getCountry()).append(";");
            }
            string.append(area.getLat()).append(";");
            string.append(area.getLng()).append(";");
            string.append(area.getRadius()).append(";");
            string.append(area.getTimeAvailable().toString());
        }
        file.write(string.toString());
    }

    private void loadVolunteer(Scanner scanner){
        String line = scanner.nextLine();
        String[] params = line.split(";");
        Volunteer volunteer = new Volunteer(params[0], params[1]);
        volunteer.setAddress(params[2]);
        volunteer.setCity(params[3]);
        volunteer.setCountry(params[4]);
        volunteer.setLat(Double.parseDouble(params[5]));
        volunteer.setLng(Double.parseDouble(params[6]));
        volunteer.setFirstName(params[7]);
        volunteer.setLastName(params[8]);
        if(scanner.hasNextLine()) {
            line = scanner.nextLine();
        }
        else{
            addVolunteer(volunteer);
            return;
        }
        if(!line.isEmpty()){
            params = line.split(";");
            int i = 0;
            while(i < params.length){
                Area area = new Area(params[i], params[i+1], params[i+2]);
                area.setLat(Double.parseDouble(params[i + 3]));
                area.setLng(Double.parseDouble(params[i + 4]));
                area.setRadius(Integer.parseInt(params[i + 5]));
                area.setTimeAvailable(Integer.parseInt(params[i +6]), Integer.parseInt(params[i + 7]), Integer.parseInt(params[i + 8]), Integer.parseInt(params[i + 9]),
                        Integer.parseInt(params[i + 10]), Integer.parseInt(params[i + 11]), Integer.parseInt(params[i + 12]), Integer.parseInt(params[i + 13]));
                area.setAreaOf(volunteer);
                volunteer.addArea(area);
                addArea(area);
                i = i + 14;
            }
        }
        addVolunteer(volunteer);
    }

    private void saveGovernment(FileWriter file, Government government) throws IOException{
        saveUser(file, government);
        file.write("\n");
        StringBuilder string = new StringBuilder();
        Iterator<RegisteredVulnerable> iterator = government.getRegisteredVulnerablesIterator();
        while(iterator.hasNext()){
            RegisteredVulnerable a = iterator.next();
            string.append(a.getFirstName()).append(";");
            string.append(a.getLastName()).append(";");
            string.append(a.getAddress()).append(";");
            string.append(a.getCity()).append(";");
            string.append(a.getCountry()).append(";");
        }
        file.write(string.toString());
    }

    private void loadGovernment(Scanner scanner){
        String line = scanner.nextLine();
        String[] params = line.split(";");
        Government government = new Government(params[0], params[1]);
        government.setAddress(params[2]);
        String city = params[3];
        String country = params[4];
        government.setCity(city);
        government.setCountry(country);
        government.setLat(Double.parseDouble(params[5]));
        government.setLng(Double.parseDouble(params[6]));
        if(scanner.hasNextLine()) {
            line = scanner.nextLine();
        }
        else{
            addGovernment(city + ", " + country, government);
            return;
        }
        if(!line.isEmpty()){
            params = line.split(";");
            int i = 0;
            while(i < params.length){
                government.addVulnerable(params[i], params[i + 1], params[i + 2], params[i + 3], params[i + 4]);
                i = i + 5;
            }
        }
        addGovernment(city + ", " + country, government);
    }

    private void saveShop(FileWriter file, Shop shop) throws IOException{
        saveUser(file, shop);
        StringBuilder string = new StringBuilder();
        string.append(";").append(shop.getType()).append(";").append(shop.getName()).append("\n");
        file.write(string.toString());
        string = new StringBuilder();
        boolean first = true;
        Iterator<String> iterator = shop.getProductsIterator();
        while (iterator.hasNext()){
            String a = iterator.next();
            if(first){
                string.append(a).append(";").append(shop.getQuantity(a));
                first = false;
            }
            else{
                string.append(";").append(a).append(";").append(shop.getQuantity(a));
            }
        }
        string.append("\n");
        file.write(string.toString());
        string = new StringBuilder();
        first = true;
        Iterator<Map.Entry<String, OpeningTime>> iterators = shop.getWorkingTimesIterator();
        while (iterators.hasNext()){
            Map.Entry a = iterators.next();
            if(first){
                string.append(a.getKey()).append(";").append(a.getValue().toString());
                first = false;
            }
            else{
                string.append(";").append(a.getKey()).append(";").append(a.getValue().toString());
            }
        }
        file.write(string.toString());
    }

    private void loadShop(Scanner scanner){
        String line = scanner.nextLine();
        String[] params = line.split(";");
        Shop shop = new Shop(params[0], params[1]);
        shop.setAddress(params[2]);
        String city = params[3];
        shop.setCity(city);
        String country = params[4];
        shop.setCountry(country);
        shop.setLat(Double.parseDouble(params[5]));
        shop.setLng(Double.parseDouble(params[6]));
        shop.setType(params[7]);
        shop.setName(params[8]);
        if(scanner.hasNextLine()) {
            line = scanner.nextLine();
        }
        else{
            addShop(city + ", " + country, shop);
            return;
        }
        if(!line.isEmpty()){
            params = line.split(";");
            int i = 0;
            while(i < params.length){
                shop.addNewProduct(params[i], Integer.parseInt(params[i + 1]));
                i = i + 2;
            }
        }
        if(scanner.hasNextLine()) {
            line = scanner.nextLine();
        }
        else{
            addShop(city + ", " + country, shop);
            return;
        }
        if(!line.isEmpty()){
            params = line.split(";");
            int i = 0;
            while(i < params.length){
                String[] times = params[i+1].split(",");
                shop.setTime(params[i], Integer.parseInt(times[0]), Integer.parseInt(times[1]), Integer.parseInt(times[2]), Integer.parseInt(times[3]));
                shop.setIsWorking(params[i], Boolean.parseBoolean(times[4]));
                i = i + 2;
            }
        }
        addShop(city + ", " + country, shop);
    }

    private void saveVulnerable(FileWriter file, Vulnerable vulnerable) throws IOException{
        saveUser(file, vulnerable);
        StringBuilder string = new StringBuilder();
        string.append(";").append(vulnerable.getFirstName()).append(";").append(vulnerable.getLastName());
        string.append("\n");
        file.write(string.toString());
        string = new StringBuilder();
        boolean first = true;
        Iterator<Request> iterator = vulnerable.getRequestsIterator();
        while(iterator.hasNext()){
            Request a = iterator.next();
            if(first){
                string.append(a.getQuantity()).append(";").append(a.getProduct()).append(";");
                string.append((ChronoUnit.DAYS.between(Calendar.getInstance().toInstant(), a.getDeliverBy().toInstant()) + 1)).append(";");
                string.append(a.getType()).append(";");
                string.append(a.getIsAccepted());
                if(a.getIsAccepted()) {
                    Volunteer b = a.getAcceptedBy();
                    string.append(";").append(b.getFirstName()).append(";").append(b.getLastName()).append(";").append(b.getAddress()).append(";");
                    string.append(b.getCity()).append(";").append(b.getCountry());
                }
                first = false;
            }
            else{
                string.append(";").append(a.getQuantity()).append(";").append(a.getProduct()).append(";");
                string.append((ChronoUnit.DAYS.between(Calendar.getInstance().toInstant(), a.getDeliverBy().toInstant()) + 1)).append(";");
                string.append(a.getIsAccepted());
                if(a.getIsAccepted()) {
                    Volunteer b = a.getAcceptedBy();
                    string.append(";").append(b.getFirstName()).append(";").append(b.getLastName()).append(";").append(b.getAddress()).append(";");
                    string.append(b.getCity()).append(";").append(b.getCountry());
                }
            }
        }
        file.write(string.toString());
    }

    private void loadVulnerable(Scanner scanner){
        String line = scanner.nextLine();
        String[] params = line.split(";");
        Vulnerable vulnerable = new Vulnerable(params[0], params[1]);
        vulnerable.setAddress(params[2]);
        String city = params[3];
        String country = params[4];
        vulnerable.setCity(city);
        vulnerable.setCountry(country);
        vulnerable.setLat(Double.parseDouble(params[5]));
        vulnerable.setLng(Double.parseDouble(params[6]));
        vulnerable.setFirstName(params[7]);
        vulnerable.setLastName(params[8]);
        if(scanner.hasNextLine()) {
            line = scanner.nextLine();
        }
        else{
            addVulnerable(city + ", " + country, vulnerable);
            return;
        }
        if(!line.isEmpty()){
            params = line.split(";");
            int i = 0;
            while (i < params.length){
                Request request = new Request(Integer.parseInt(params[i]), params[i + 1], Integer.parseInt(params[i + 2]), params[i+3]);
                if(Boolean.parseBoolean(params[i+4])){
                    Volunteer volunteer = searchVolunteer(params[i+5], params[i+6], params[i+7], params[i+8], params[i+9]);

                    i = i + 5;
                }
                else{
                    request.setNotAccepted();
                }
                i = i + 5;
                request.setRequestedBy(vulnerable);
                vulnerable.addRequest(request);
            }
        }
        addVulnerable(city + ", " + country, vulnerable);
    }

    public void saveVolunteers(){
        boolean first = true;
        try {
            FileWriter file = new FileWriter("Volunteers.txt");
            Iterator<Container> iterator = volunteers.values().iterator();
            while(iterator.hasNext()) {
                Container a = iterator.next();
                Iterator<User> iterators = a.iterator();
                while (iterators.hasNext()){
                    Volunteer b = (Volunteer) iterators.next();
                    if (first) {
                        saveVolunteer(file, b);
                        first = false;
                    } else {
                        file.write("\n");
                        saveVolunteer(file, b);
                    }
                }
            }
            file.close();
        }catch (IOException e){
            System.out.println("Failed to save volunteers.");
        }
    }

    private void loadVolunteers(){
        File read = new File("Volunteers.txt");
        Scanner scan = null;
        try {
            scan = new Scanner(read);
        } catch (IOException e) {
            System.out.println("Volunteers file not found.");
        }
        while (scan.hasNextLine()){
            loadVolunteer(scan);
        }
    }

    public void saveGovernments(){
        boolean first = true;
        try {
            FileWriter file = new FileWriter("Governments.txt");
            for (Government a : governments.values()) {
                if (first) {
                    saveGovernment(file, a);
                    first = false;
                } else {
                    file.write("\n");
                    saveGovernment(file, a);
                }
            }
            file.close();
        }catch (IOException e){
            System.out.println("Failed to save governments.");
        }
    }

    private void loadGovernments(){
        File read = new File("Governments.txt");
        Scanner scan = null;
        try {
            scan = new Scanner(read);
        } catch (IOException e) {
            System.out.println("Governments file not found.");
        }
        while (scan.hasNextLine()){
            loadGovernment(scan);
        }
    }

    public void saveShops(){
        boolean first = true;
        try {
            FileWriter file = new FileWriter("Shops.txt");
            Iterator<Container> iterator = shops.values().iterator();
            while(iterator.hasNext()) {
                Iterator<User> iterators = iterator.next().iterator();
                while (iterators.hasNext()) {
                    Shop a = (Shop) iterators.next();
                    if (first) {
                        saveShop(file, a);
                        first = false;
                    } else {
                        file.write("\n");
                        saveShop(file, a);
                    }
                }
            }
            file.close();
        }catch (IOException e){
            System.out.println("Failed to save shops.");
        }
    }

    private void loadShops(){
        File read = new File("Shops.txt");
        Scanner scan = null;
        try {
            scan = new Scanner(read);
        } catch (IOException e) {
            System.out.println("Shops file not found.");
        }
        while (scan.hasNextLine()){
            loadShop(scan);
        }
    }

    public void saveVulnerables(){
        boolean first = true;
        try {
            FileWriter file = new FileWriter("Vulnerables.txt");
            Iterator<Container> iterator = vulnerables.values().iterator();
            while(iterator.hasNext()) {
                Iterator<User> iterators = iterator.next().iterator();
                while (iterators.hasNext()) {
                    Vulnerable a = (Vulnerable) iterators.next();
                    if (first) {
                        saveVulnerable(file, a);
                        first = false;
                    } else {
                        file.write("\n");
                        saveVulnerable(file, a);
                    }
                }
            }
            file.close();
        }catch (IOException e){
            System.out.println("Failed to save vulnerables.");
        }
    }

    private void loadVulnerables(){
        File read = new File("Vulnerables.txt");
        Scanner scan = null;
        try {
            scan = new Scanner(read);
        } catch (IOException e) {
            System.out.println("Vulnerables file not found.");
        }
        while (scan.hasNextLine()){
            loadVulnerable(scan);
        }
    }

    private void loadData(){
        loadShops();
        loadGovernments();
        loadVolunteers();
        loadVulnerables();
    }

    public ArrayList<User> listVulnerablesInArea(double lat, double lng, int radius, String city){
        return vulnerables.get(city).listSuitable(lat, lng, radius);
    }

    public ArrayList<User> listVolunteersInArea(double lat, double lng, int radius, String city){
        return volunteers.get(city).listSuitable(lat, lng, radius);
    }

    public ArrayList<User> listShopsInArea(double lat, double lng, int radius, String city){
        return shops.get(city).listSuitable(lat, lng, radius);
    }
}
