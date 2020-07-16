package ucl.ac.uk.main;

import java.util.Calendar;
import java.util.Objects;

public class Request {
    private int quantity;
    private String product;
    private String type;
    private Calendar deliverBy;
    private boolean isAccepted;
    private Volunteer acceptedBy;
    private Vulnerable requestedBy;
    private Shop buyFrom;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request)) return false;
        Request request = (Request) o;
        return getQuantity() == request.getQuantity() &&
                isAccepted == request.isAccepted &&
                getProduct().equals(request.getProduct()) &&
                getType().equals(request.getType()) &&
                getDeliverBy().equals(request.getDeliverBy()) &&
                Objects.equals(getRequestedBy(), request.getRequestedBy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuantity(), getProduct(), getType(), getDeliverBy(), isAccepted, getRequestedBy());
    }

    public Request(int quantity, String product, int daysToWait, String type){
        this.quantity = quantity;
        this.product = product;
        this.type = type;
        this.deliverBy = Calendar.getInstance();
        deliverBy.add(Calendar.DATE, daysToWait);
        this.isAccepted = false;
        this.acceptedBy = null;
        this.requestedBy = null;
    }

    public int getQuantity(){
        return quantity;
    }

    public String getProduct(){
        return product;
    }

    public String getType() {
        return type;
    }

    public Calendar getDeliverBy() {
        return deliverBy;
    }

    public boolean getIsAccepted(){
        return isAccepted;
    }

    public Vulnerable getRequestedBy(){
        return requestedBy;
    }

    public Volunteer getAcceptedBy(){
        return acceptedBy;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public void setDaysToWait(int daysToWait){
        deliverBy = Calendar.getInstance();
        deliverBy.add(Calendar.DATE, daysToWait);
    }

    public void setAccepted(Volunteer a){
        this.isAccepted = true;
        this.acceptedBy = a;
    }

    public void setNotAccepted(){
        this.isAccepted = false;
        this.acceptedBy = null;
    }

    public void setRequestedBy(Vulnerable vulnerable){
        this.requestedBy = vulnerable;
    }

    public Shop getBuyFrom() {
        return buyFrom;
    }

    public void setBuyFrom(Shop buyFrom) {
        this.buyFrom = buyFrom;
    }
}
