package waw.decision.maker.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kwalczak on 21.03.15.
 */
public class LoanApplicationReport {

    private String loanId;
    private String job;
    private double amount;
    private String fraudStatus;
    private String decision;


    public LoanApplicationReport(LoanApplication loanApplication, String decision, String loanId){
        this.loanId = loanId;
        this.job = loanApplication.getJob();
        this.amount = loanApplication.getAmount();
        this.fraudStatus = loanApplication.getFraudStatus();
        this.decision = decision;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFraudStatus() {
        return fraudStatus;
    }

    public void setFraudStatus(String fraudStatus) {
        this.fraudStatus = fraudStatus;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public Map<String, String> toMap(){
        Map<String, String> map = new HashMap<>();
        map.put("loanId", loanId);
        map.put("fraudStatus", fraudStatus);
        return map;
    }
}
