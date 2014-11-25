package org.gradoop.foodbroker.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by peet on 18.11.14.
 */
public class SalesQuotation extends AbstractTransactionalDataObject {

    private Date date;
    private String num;
    private Employee sentBy;
    private Customer sentTo;

    public SalesQuotation(){
        this.num = getBusinessKey("SQN",12);
    }

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = super.getProperties();
        properties.put("date",date);
        properties.put("num",num);
        properties.put("sentBy",sentBy);
        properties.put("sentTo",sentTo);
        return properties;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public String getNum() {
        return num;
    }

    public Employee getSentBy() {
        return sentBy;
    }

    public void setSentBy(Employee sentBy) {
        this.sentBy = sentBy;
    }

    public Customer getSentTo() {
        return sentTo;
    }

    public void setSentTo(Customer sentTo) {
        this.sentTo = sentTo;
    }
}
