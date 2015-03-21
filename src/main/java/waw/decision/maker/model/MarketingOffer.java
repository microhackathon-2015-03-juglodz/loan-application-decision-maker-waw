package waw.decision.maker.model;

/**
 * Created by kwalczak on 21.03.15.
 */
public class MarketingOffer {
    private Person person = new Person();
    private String decision;


    public MarketingOffer(LoanApplication application, String status){
        person.setFirstName(application.getFirstName());
        person.setLastName(application.getLastName());
        decision = status;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getFirstName() {
        return person.getFirstName();
    }

    public void setFirstName(String firstName) {
        person.setFirstName(firstName);
    }

    public String getLastName() {
        return person.getLastName();
    }

    public void setLastName(String lastName) {
        person.setLastName(lastName);
    }
}
