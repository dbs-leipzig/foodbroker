package org.biiig.foodbroker.model;

import java.util.Date;

/**
 * Created by peet on 02.12.14.
 */
public class Ticket extends AbstractCITDataObject {

    public Ticket(SalesOrder salesOrder) {
        this.properties.put("erpSoNum", salesOrder.getProperty("num"));
        this.properties.put("id",id);
        this.properties.put("kind","TransData");
    }

    @Override
    public Object getLocalID() {
        return this.properties.get("id");
    }

    public void setCreatedAt(Date date) {
        this.properties.put("createdAt",date);
    }

    public void setCreatedBy(User createdBy) {
        this.nestedRelationships.put("createdBy",createdBy);
    }

    public void setAllocatedTo(User allocatedTo) {
        this.nestedRelationships.put("allocatedTo",allocatedTo);
    }

    public void setOpenedBy(Client openedBy) {
        this.nestedRelationships.put("openedBy",openedBy);
    }

    public void setProblem(String problem) {
        this.properties.put("problem",problem);
    }

    public Date getCreatedAt() {
        return (Date) this.properties.get("createdAt");
    }

    public User getAllocatedTo (){
        return (User) this.nestedRelationships.get("allocatedTo");
    }
}
