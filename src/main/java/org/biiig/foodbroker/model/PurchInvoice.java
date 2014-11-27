package org.biiig.foodbroker.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Created by peet on 21.11.14.
 */
public class PurchInvoice extends AbstractERPTransactionalDataObject {

    public PurchInvoice() {
        setNum("PIN", 12);
    }

    public void setExpense(BigDecimal expense) {
        this.properties.put("expense", expense);
    }


    public void setCreatedFor(PurchOrder createdFor) {
        this.nestedRelationships.put("createdFor", createdFor);
    }

    public void setText(String text) {
        this.properties.put("text", text);
    }
}