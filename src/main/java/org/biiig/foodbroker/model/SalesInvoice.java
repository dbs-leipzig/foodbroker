package org.biiig.foodbroker.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Created by peet on 25.11.14.
 */
public class SalesInvoice extends AbstractERPTransactionalDataObject {

    public SalesInvoice() {
        setNum("SIN",12);
    }

    public void setText(String text) {
        properties.put("text", text);
    }

    public BigDecimal getRevenue() {
        return (BigDecimal) this.properties.get("revenue");
    }

    public void setRevenue(BigDecimal revenue) {
        properties.put("revenue", revenue);
    }

    public void setCreatedFor(SalesOrder createdFor) {
        this.nestedRelationships.put("createdFor", createdFor);
    }
}