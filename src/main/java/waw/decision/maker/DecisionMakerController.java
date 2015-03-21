package waw.decision.maker;

import org.springframework.web.bind.annotation.RequestMapping;
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
}