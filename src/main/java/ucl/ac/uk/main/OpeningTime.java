package ucl.ac.uk.main;

public class OpeningTime {
    private int openingHour;
    private int openingMinute;
    private int closingHour;
    private int closingMinute;
    private boolean isWorking;

    public OpeningTime(){
        this.openingHour = 9;
        this.openingMinute = 0;
        this.closingHour = 18;
        this.closingMinute = 0;
        this.isWorking = true;
    }

    public OpeningTime(int openingHour, int openingMinute, int closingHour, int closingMinute){
        this.openingHour = openingHour;
        this.openingMinute = openingMinute;
        this.closingHour = closingHour;
        this.closingMinute = closingMinute;
        this.isWorking = true;
    }

    public int getOpeningHour(){
        return this.openingHour;
    }

    public int getOpeningMinute(){
        return this.openingMinute;
    }

    public void setOpeningTime(String a){
        String[] as = a.split(":");
        this.openingHour = Integer.parseInt(as[0]);
        this.openingMinute = Integer.parseInt(as[1]);
    }

    public void setClosingTime(String a){
        String[] as = a.split(":");
        this.closingHour = Integer.parseInt(as[0]);
        this.closingMinute = Integer.parseInt(as[1]);
    }

    public boolean getIsWorking(){
        return this.isWorking;
    }

    public void setWorking(boolean a){
        this.isWorking = a;
    }

    public int getClosingHour(){
        return this.closingHour;
    }

    public int getClosingMinute(){
        return this.closingMinute;
    }

    public String getOpeningTime(){
            if(openingHour >= 10 && openingMinute >= 10) {
                return openingHour + ":" + openingMinute;
            }
            else if(openingHour >= 10 && openingMinute < 10){
                return openingHour + ":0" + openingMinute;
            }
            else if(openingHour < 10 && openingMinute >= 10){
                return "0" + openingHour + ":" + openingMinute;
            }
            else{
                return "0" + openingHour + ":0" + openingMinute;
            }
    }

    public String getClosingTime(){
        if(closingHour >= 10 && closingMinute >= 10){
            return closingHour + ":" + closingMinute;
        }
        else if(closingHour >= 10 && closingMinute < 10){
            return closingHour + ":0" + closingMinute;
        }
        else if(closingHour < 10 && closingMinute >= 10){
            return "0" + closingHour + ":" + closingMinute;
        }
        else{
            return "0" + closingHour + ":0" + closingMinute;
        }
    }

    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append(openingHour).append(",");
        result.append(openingMinute).append(",");
        result.append(closingHour).append(",");
        result.append(closingMinute).append(",");
        result.append(isWorking);
        return result.toString();
    }
}
