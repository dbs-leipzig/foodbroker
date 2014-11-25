package org.gradoop.foodbroker.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by peet on 20.11.14.
 */
public class SalesOrder extends AbstractTransactionalDataObject {


    private String num;
    private Date date;
    private Date deliveryDate;
    private Employee processedBy;
    private Customer receivedFrom;
    private SalesQuotation basedOn;

    public SalesOrder(){
        this.num = getBusinessKey("SOR",12);
    }

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = super.getProperties();
        properties.put("date",date);
        properties.put("num",num);
        properties.put("deliveryDate",deliveryDate);
        properties.put("processedBy",processedBy);
        properties.put("receivedFrom",receivedFrom);
        properties.put("basedOn",basedOn);
        return properties;
    }


    public String getNum() {
        return num;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Employee getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(Employee processedBy) {
        this.processedBy = processedBy;
    }

    public Customer getReceivedFrom() {
        return receivedFrom;
    }

    public void setReceivedFrom(Customer receivedFrom) {
        this.receivedFrom = receivedFrom;
    }

    public SalesQuotation getBasedOn() {
        return basedOn;
    }

    public void setBasedOn(SalesQuotation basedOn) {
        this.basedOn = basedOn;
    }
}
