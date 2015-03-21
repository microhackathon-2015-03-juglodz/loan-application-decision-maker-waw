package waw.decision.maker;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kwalczak on 21.03.15.
 */
@RestController
public class DecisionMakerController {


    @RequestMapping("/test")
    public String greeting() {
        return  "working";
    }




    @RequestMapping(value = "/api/loanApplication/{loanApplicationId}", method = RequestMethod.GET)
    public LoanDecission getLoanApplication(@PathVariable String loanApplicationId){

        LoanDecission loanApplication = new LoanDecission();
        loanApplication.setApplicationId(loanApplicationId);

        return loanApplication;

    }


}