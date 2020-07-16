package ucl.ac.uk.main;

import java.util.*;

public class Shop extends User{
    private String name;
    private String type;
    private HashMap<String, Integer> products;
    private HashMap<String, OpeningTime> workingTimes;

    public Shop(String username, String password){
        super(username, password);
        products = new HashMap<>();
        workingTimes = new HashMap<>();
        setInitialTimes();
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void addNewProduct(String name, int quantity){
        products.put(name, quantity);
    }

    public void setQuantity(String product, int quantity){
        products.put(product, quantity);
    }

    public Set<String> getProducts(){
        return products.keySet();
    }

    public int getQuantity(String product){
        return products.get(product);
    }

    public void removeProduct(String name){
        products.remove(name);
    }

    public Iterator<String> getProductsIterator(){
        return products.keySet().iterator();
    }

    public Iterator<Map.Entry<String, OpeningTime>> getWorkingTimesIterator(){
        return workingTimes.entrySet().iterator();
    }

    private void setInitialTimes(){
        workingTimes.put("Monday", new OpeningTime());
        workingTimes.put("Tuesday", new OpeningTime());
        workingTimes.put("Wednesday", new OpeningTime());
        workingTimes.put("Thursday", new OpeningTime());
        workingTimes.put("Friday", new OpeningTime());
        workingTimes.put("Saturday", new OpeningTime());
        workingTimes.put("Sunday", new OpeningTime());
    }

    public void setTime(String day, int startHour, int startMinute, int finishHour, int finishMinute){
        OpeningTime time = workingTimes.get(day);
        time.setOpeningTime(startHour + ":" + startMinute);
        time.setClosingTime(finishHour + ":" + finishMinute);
    }

    public void setOpeningTime(String day, String time){
        workingTimes.get(day).setOpeningTime(time);
    }

    public String getOpeningTime(String day){
        return workingTimes.get(day).getOpeningTime();
    }

    public String getClosingTime(String day){
        return workingTimes.get(day).getClosingTime();
    }

    public void setClosingTime(String day, String time){
        workingTimes.get(day).setClosingTime(time);
    }

    public boolean getIsWorking(String day){
        return workingTimes.get(day).getIsWorking();
    }

    public void setIsWorking(String day, boolean a){
        workingTimes.get(day).setWorking(a);
    }

    public int getStartHour(String day){
        return workingTimes.get(day).getOpeningHour();
    }

    public int getStartMinute(String day){
        return workingTimes.get(day).getOpeningMinute();
    }

    public int getFinishHour(String day){
        return workingTimes.get(day).getClosingHour();
    }

    public int getFinishMinute(String day){
        return workingTimes.get(day).getClosingMinute();
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

    public String getOpeningTime(int day){
        return getOpeningTime(dayToDay(day));
    }

    public String getClosingTime(int day){
        return getClosingTime(dayToDay(day));
    }

    public boolean isOpen(int day, int hour, int min){
        String day1 = dayToDay(day);
        OpeningTime time = workingTimes.get(day1);
        int openHour = time.getOpeningHour();
        int closeHour = time.getClosingHour();
        int openMin = time.getOpeningMinute();
        int closeMin = time.getClosingMinute();
        if(openHour > hour || closeHour < hour){
            return false;
        }
        else if(openHour == hour && openMin > min){
            return false;
        }
        else if(closeHour == hour && closeMin < min){
            return false;
        }
        return true;
    }
}
