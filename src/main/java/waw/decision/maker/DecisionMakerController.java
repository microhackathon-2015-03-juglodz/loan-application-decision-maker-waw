package waw.decision.maker;

import com.github.fakemongo.Fongo;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient;
import groovy.lang.Closure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import waw.decision.maker.model.LoanApplication;
import waw.decision.maker.model.LoanApplicationReport;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kwalczak on 21.03.15.
 */
@RestController
public class DecisionMakerController {

    private static final Logger LOG = LoggerFactory.getLogger(DecisionMakerController.class);

    @Autowired
    ServiceRestClient serviceRestClient;

    private static final String FRAUD = "fraud";
    private static final String GOOD = "good";
    private static final String FISHY = "fishy";

    private static final String FAILURE = "failure";
    private static final String MANUAL = "manual";
    private static final String SUCCESS = "success";

    private Map<String, LoanApplicationReport> reports = new HashMap<>();


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
        reports.put(loanApplicationId, report);
        serviceRestClient.forService("reporting-service")
                .post()
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

    }

    @RequestMapping(value = "/api/loanApplication/{loanApplicationId}", method = RequestMethod.GET)
    public LoanDecission getLoanApplication(@PathVariable String loanApplicationId){

        LoanDecission loanApplication = new LoanDecission();
        loanApplication.setApplicationId(loanApplicationId);

        return loanApplication;

    }

}