package org.biiig.foodbroker.model;

import java.util.Date;
import java.util.Map;

/**
 * Created by peet on 18.11.14.
 */
public class SalesQuotation extends AbstractERPTransactionalDataObject {

    public SalesQuotation(){
        this.setNum("SQN",12);
    }

    public Employee getSentBy() {
        return (Employee) this.nestedRelationships.get("sentBy");
    }

    public void setSentBy(Employee sentBy) {
        this.nestedRelationships.put("sentBy",sentBy);
    }

    public Customer getSentTo() {
        return (Customer) this.nestedRelationships.get("sentTo");
    }

    public void setSentTo(Customer sentTo) {
        this.nestedRelationships.put("sentTo",sentTo);
    }
}
