package ucl.ac.uk.main;

public class RegisteredVulnerable {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String country;

    public RegisteredVulnerable(String firstName, String lastName, String address, String country, String city){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.country = country;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public String getAddress(){
        return this.address;
    }

    public String getCity(){
        return this.city;
    }

    public String getCountry(){
        return this.country;
    }

    public boolean equals(RegisteredVulnerable a){
        if(!this.firstName.equals(a.firstName)){
            return false;
        }
        if(!this.lastName.equals(a.lastName)){
            return false;
        }
        if(!this.address.equals(a.address)){
            return false;
        }
        if(!this.city.equals(a.city)){
            return false;
        }
        if(!this.country.equals(a.country)){
            return false;
        }
        return true;
    }
}
