package org.biiig.foodbroker.model;

import java.util.Date;
import java.util.Map;

/**
 * Created by peet on 21.11.14.
 */
public class PurchOrder extends AbstractERPTransactionalDataObject {

    public PurchOrder(){
        setNum("POR",12);
    }

    public Employee getProcessedBy() {
        return (Employee) this.nestedRelationships.get("processedBy");
    }

    public void setProcessedBy(Employee processedBy) {
        this.nestedRelationships.put("processedBy",processedBy);
    }

    public Vendor getPlacedAt() {
        return (Vendor) this.nestedRelationships.get("placedAt");
    }

    public void setPlacedAt(Vendor placedAt) {
        this.nestedRelationships.put("placedAt",placedAt);
    }

    public void setServes(SalesOrder serves) {
        this.nestedRelationships.put("serves",serves);
    }
}
