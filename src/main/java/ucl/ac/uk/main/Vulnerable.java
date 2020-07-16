package ucl.ac.uk.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class Vulnerable extends User {
    private String firstName;
    private String lastName;
    private ArrayList<Request> requests;

    public Vulnerable(String username, String password){
        super(username, password);
        this.requests = new ArrayList<>();
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vulnerable)) return false;
        Vulnerable that = (Vulnerable) o;
        return getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName());
    }

    public void addRequest(String product, int quantity, int daysToWait, String type){
        Request request = new Request(quantity, product, daysToWait, type);
        request.setRequestedBy(this);
        requests.add(request);
    }

    public void addRequest(Request request){
        request.setRequestedBy(this);
        requests.add(request);
    }

    public void removeRequest(Request request){
        requests.remove(request);
    }

    public void removeRequest(int index){
        requests.remove(index);
    }

    public Iterator<Request> getRequestsIterator(){
        return requests.iterator();
    }

}
