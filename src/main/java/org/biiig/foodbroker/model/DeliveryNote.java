package org.biiig.foodbroker.model;

import java.util.Date;
import java.util.Map;

/**
 * Created by peet on 21.11.14.
 */
public class DeliveryNote extends AbstractERPTransactionalDataObject {

    public DeliveryNote(){
        setNum("DLV",12);
    }

    public Logistics getOperatedBy() {
        return (Logistics) this.nestedRelationships.get("operatedBy");
    }

    public void setOperatedBy(Logistics operatedBy) {
        this.nestedRelationships.put("operatedBy",operatedBy);
    }


    public void setContains(PurchOrder contains) {
        this.nestedRelationships.put("contains",contains);
    }

    public void setTrackingCode(String trackingCode) {
        this.properties.put("trackingCode",trackingCode);
    }

    public PurchOrder getContains() {
        return (PurchOrder) this.nestedRelationships.get("contains");
    }
}
