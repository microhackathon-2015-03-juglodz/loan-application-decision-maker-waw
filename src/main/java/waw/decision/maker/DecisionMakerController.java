package waw.decision.maker;

import com.netflix.hystrix.HystrixCommand;
import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient;
import groovy.lang.Closure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import waw.decision.maker.data.LoadDecissionDB;
import waw.decision.maker.model.LoanApplication;
import waw.decision.maker.model.LoanApplicationReport;
import waw.decision.maker.model.LoanDecission;
import waw.decision.maker.model.MarketingOffer;

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

    @Autowired
    ServiceRestClient serviceRestClient;


    private static final Logger LOG = LoggerFactory.getLogger(DecisionMakerController.class);


    @RequestMapping(value = "/api/loanApplication/{loanApplicationId}", method = RequestMethod.PUT)
    public void decide(@PathVariable String loanApplicationId, @RequestBody LoanApplication loanApplication) {
        String loanStatus;
        switch (loanApplication.getFraudStatus()) {
            case FRAUD:
                loanStatus = FAILURE;
                break;
            case GOOD:
                loanStatus = SUCCESS;
                break;
            case FISHY:
                loanStatus = MANUAL;
                break;
            default:
                loanStatus = MANUAL;
        }
        LoanApplicationReport report = new LoanApplicationReport(loanApplication, loanStatus, loanApplicationId);
        LoanDecission loanDecission = new LoanDecission(loanApplicationId, "", loanStatus);
        loadDecissionDB.insert(loanDecission);
        serviceRestClient.forService("reporting-service")
                .put()
                .withCircuitBreaker(
                        HystrixCommand.Setter.withGroupKey(() -> "group").andCommandKey(() -> "command"),
                        new Closure(this) {
                            @Override
                            public void run() {
                                LOG.info("BREAKING DA CIRCUIT!");
                            }
                        })

                .onUrl("/api/reporting")
                .body(report)
                .withHeaders()
                .contentTypeJson();
        MarketingOffer offer = new MarketingOffer(loanApplication, loanStatus);

        serviceRestClient.forService("marketing-offer-generator")
                .put()
                .withCircuitBreaker(
                        HystrixCommand.Setter.withGroupKey(() -> "group").andCommandKey(() -> "command"),
                        new Closure(this) {
                            @Override
                            public void run() {
                                LOG.info("BREAKING DA CIRCUIT!");
                            }
                        })

                .onUrl("/api/marketing/" + loanApplicationId)
                .body(offer)
                .withHeaders()
                .contentTypeJson();
    }

    @RequestMapping(value = "/api/loanApplication/{loanApplicationId}", method = RequestMethod.GET)
    public LoanDecission getLoanDecission(@PathVariable String loanApplicationId) {
        LoanDecission loanDecission = loadDecissionDB.selectSingle(loanApplicationId);
        return loanDecission;

    }

}