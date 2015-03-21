package waw.decision.maker;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import waw.decision.maker.data.LoadDecissionDB;
import waw.decision.maker.model.LoanApplication;
import waw.decision.maker.model.LoanDecission;

/**
 * Created by kwalczak on 21.03.15.
 */
@RestController
public class DecisionMakerController {

    private static final String FRAUD = "fraud";
    private static final String GOOD = "good";
    private static final String FISHY = "fishy";

    private static final String FAILURE = "failure";
    private static final String MANUAL = "manual";
    private static final String SUCCESS = "success";

    @Autowired
    private LoadDecissionDB loadDecissionDB;

    @RequestMapping("/test")
    public String greeting() {
        return "working";
    }

    @RequestMapping(value = "/api/loanApplication/{loanApplicationId}", method = RequestMethod.PUT)
    public void decide(@PathVariable String loanApplicationId, @RequestBody LoanApplication loanApplication) {
        String loanStatus;
        switch (loanApplication.getFraudStatus()) {
            case FRAUD:
                loanStatus = FAILURE;
            case GOOD:
                loanStatus = SUCCESS;
            case FISHY:
                loanStatus = MANUAL;
            default:
                loanStatus = MANUAL;
        }
        //insert to db
        //call report
    }

    @RequestMapping(value = "/api/loanApplication/{loanApplicationId}", method = RequestMethod.GET)
    public LoanDecission getLoanDecission(@PathVariable String loanApplicationId){

        LoanDecission loanDecission = loadDecissionDB.selectSingle(loanApplicationId);

        return loanDecission;

    }

}