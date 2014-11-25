package org.gradoop.foodbroker.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Created by peet on 21.11.14.
 */
public class PurchInvoice extends AbstractTransactionalDataObject {

    private String num;
    private Date date;
    private BigDecimal expense;
    private PurchOrder createdFor;
    private String text;

    public PurchInvoice() {
        this.num = getBusinessKey("PIN", 12);
    }

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = super.getProperties();
        properties.put("date", date);
        properties.put("num", num);
        properties.put("expense", expense);
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

    public BigDecimal getExpense() {
        return expense;
    }

    public void setExpense(BigDecimal expense) {
        this.expense = expense;
    }

    public PurchOrder getCreatedFor() {
        return createdFor;
    }

    public void setCreatedFor(PurchOrder createdFor) {
        this.createdFor = createdFor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}