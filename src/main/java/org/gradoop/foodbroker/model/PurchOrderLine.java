package org.gradoop.foodbroker.model;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by peet on 21.11.14.
 */
public class PurchOrderLine extends AbstractPropertyContainer implements Relationship {

    private int quantity;
    private BigDecimal purchPrice;
    private PurchOrder partOf;
    private Product contains;


    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = super.getProperties();
        properties.put("quantity",quantity);
        properties.put("purchPrice",purchPrice);
        return properties;
    }

    @Override
    public DataObject getStartDataObject() {
        return partOf;
    }

    @Override
    public DataObject getEndDataObject() {
        return contains;
    }

    @Override
    public String getLabel(){
        return this.getClass().getSimpleName();
    }

    public BigDecimal getPurchAmount(){
        return BigDecimal.valueOf(quantity).multiply(purchPrice);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPurchPrice() {
        return purchPrice;
    }

    public void setPurchPrice(BigDecimal purchPrice) {
        this.purchPrice = purchPrice;
    }

    public PurchOrder getPartOf() {
        return partOf;
    }

    public void setPartOf(PurchOrder partOf) {
        this.partOf = partOf;
    }

    public Product getContains() {
        return contains;
    }

    public void setContains(Product contains) {
        this.contains = contains;
    }
}
