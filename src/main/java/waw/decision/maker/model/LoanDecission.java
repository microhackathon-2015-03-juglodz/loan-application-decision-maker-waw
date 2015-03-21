package waw.decision.maker.model;

/**
 * Created by tomic on 2015-03-21.
 */
public class LoanDecission {

    private String applicationId;
    private String text;
    private String result;

    public LoanDecission(String applicationId, String text, String result) {
        this.applicationId = applicationId;
        this.text = text;
        this.result = result;
    }

    public LoanDecission() {
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
