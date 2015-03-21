package waw.decision.maker.data;

import org.springframework.stereotype.Repository;
import waw.decision.maker.model.LoanDecission;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tomic on 2015-03-21.
 */
@Repository
public class LoadDecissionDB {


    private Map<String, LoanDecission> data;

    @PostConstruct
    public void mockdata(){
        this.data = new HashMap<>();

        this.data.put("1", new LoanDecission("1", "text1", "success"));
        this.data.put("2", new LoanDecission("2", "text2", "failure"));
        this.data.put("3", new LoanDecission("3", "text3", "manual"));
    }

    public void insert(LoanDecission loanDecission){
        insert(loanDecission.getApplicationId(), loanDecission);
    }

    public void insert(String key, LoanDecission loanDecission){
        data.put(key, loanDecission);
    }

    public LoanDecission selectSingle(String key){
        return data.containsKey(key) ? data.get(key) : null;
    }

}
