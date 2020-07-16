package ucl.ac.uk.main;

import java.util.ArrayList;
import java.util.Iterator;

public class Government extends User{

    private ArrayList<RegisteredVulnerable> registeredVulnerables;

    public Government(String username, String password){
        super(username, password);
        registeredVulnerables = new ArrayList<>();
    }

    public void addVulnerable(String firstName, String lastName, String address, String city, String country){
        RegisteredVulnerable a = new RegisteredVulnerable(firstName, lastName, address, country, city);
        registeredVulnerables.add(a);
    }

    public Iterator<RegisteredVulnerable> getRegisteredVulnerablesIterator(){
        return registeredVulnerables.iterator();
    }

    public void removeVulnerable(String firstName, String lastName, String address, String city, String country){
        RegisteredVulnerable a = new RegisteredVulnerable(firstName, lastName, address, country, city);
        for(RegisteredVulnerable b : registeredVulnerables){
            if(b.equals(a)){
                registeredVulnerables.remove(b);
                return;
            }
        }
    }

    public void removeVulnerable(int index){
        registeredVulnerables.remove(index);
    }

    public void removeVulnerable(RegisteredVulnerable registeredVulnerable){
        registeredVulnerables.remove(registeredVulnerable);
    }

    public boolean isVulnerableRegistered(Vulnerable vulnerable){
        for(RegisteredVulnerable a : registeredVulnerables){
            if(a.getFirstName().equals(vulnerable.getFirstName())){
                if(a.getLastName().equals(vulnerable.getLastName())){
                    if(a.getAddress().equals(vulnerable.getAddress())){
                        registeredVulnerables.remove(a);
                        return  true;
                    }
                }
            }
        }
        return false;
    }
}
