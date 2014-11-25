package org.gradoop.foodbroker.model;

import java.util.Date;
import java.util.Map;

/**
 * Created by peet on 21.11.14.
 */
public class DeliveryNote extends AbstractTransactionalDataObject {

    private String num;
    private Date date;
    private Logistics operatedBy;
    private PurchOrder contains;
    private String trackingCode;

    public DeliveryNote(){
        this.num = getBusinessKey("DLV",12);
    }

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = super.getProperties();
        properties.put("date",date);
        properties.put("num",num);
        properties.put("contains",contains);
        properties.put("operatedBy",operatedBy);
        properties.put("trackingCode",trackingCode);
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

    public Logistics getOperatedBy() {
        return operatedBy;
    }

    public void setOperatedBy(Logistics operatedBy) {
        this.operatedBy = operatedBy;
    }

    public PurchOrder getContains() {
        return contains;
    }

    public void setContains(PurchOrder contains) {
        this.contains = contains;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }
}
