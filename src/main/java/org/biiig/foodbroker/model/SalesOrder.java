package org.biiig.foodbroker.model;

import java.util.*;

/**
 * Created by peet on 20.11.14.
 */
public class SalesOrder extends AbstractERPTransactionalDataObject {

    private List<SalesOrderLine> lines = new ArrayList<>();

    public SalesOrder(){
        this.setNum("SOR", 12);
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.properties.put("deliveryDate",deliveryDate);
    }

    public Employee getProcessedBy() {
        return (Employee) this.nestedRelationships.get("processedBy");
    }

    public void setProcessedBy(Employee processedBy) {
        this.nestedRelationships.put("processedBy",processedBy);
    }

    public Customer getReceivedFrom() {
        return (Customer) this.nestedRelationships.get("receivedFrom");
    }

    public void setReceivedFrom(Customer receivedFrom) {
        this.nestedRelationships.put("receivedFrom",receivedFrom);
    }

    public void setBasedOn(SalesQuotation basedOn) {
        this.nestedRelationships.put("basedOn",basedOn);
    }

    public Date getDeliveryDate() {
        return (Date) this.properties.get("deliveryDate");
    }

    public void addLine(SalesOrderLine line){
        this.lines.add(line);
    }

    public List<SalesOrderLine> getLines() {
        return lines;
    }
}
