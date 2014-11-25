package org.gradoop.foodbroker.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Created by peet on 25.11.14.
 */
public class SalesInvoice extends AbstractTransactionalDataObject {

    private String num;
    private Date date;
    private BigDecimal revenue;
    private SalesOrder createdFor;
    private String text;

    public SalesInvoice() {
        this.num = getBusinessKey("SIN", 12);
    }

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = super.getProperties();
        properties.put("date", date);
        properties.put("num", num);
        properties.put("revenue", revenue);
        properties.put("createdFor", createdFor);
        properties.put("text", text);
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    public SalesOrder getCreatedFor() {
        return createdFor;
    }

    public void setCreatedFor(SalesOrder createdFor) {
        this.createdFor = createdFor;
    }
}