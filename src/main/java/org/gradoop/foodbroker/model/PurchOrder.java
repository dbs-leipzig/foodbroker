package org.gradoop.foodbroker.model;

import java.util.Date;
import java.util.Map;

/**
 * Created by peet on 21.11.14.
 */
public class PurchOrder extends AbstractTransactionalDataObject {

    private String num;
    private Date date;
    private Employee processedBy;
    private Vendor placesAt;
    private SalesOrder serves;

    public PurchOrder(){
        this.num = getBusinessKey("POR",12);
    }

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = super.getProperties();
        properties.put("date",date);
        properties.put("num",num);
        properties.put("processedBy",processedBy);
        properties.put("serves",serves);
        return properties;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Employee getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(Employee processedBy) {
        this.processedBy = processedBy;
    }

    public Vendor getPlacesAt() {
        return placesAt;
    }

    public void setPlacesAt(Vendor placesAt) {
        this.placesAt = placesAt;
    }

    public SalesOrder getServes() {
        return serves;
    }

    public void setServes(SalesOrder serves) {
        this.serves = serves;
    }
}
