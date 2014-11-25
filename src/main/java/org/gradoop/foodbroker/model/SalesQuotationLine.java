package org.gradoop.foodbroker.model;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by peet on 20.11.14.
 */
public class SalesQuotationLine extends AbstractPropertyContainer implements Relationship {

    private int quantity;
    private BigDecimal salesPrice;
    private BigDecimal purchPrice;
    private SalesQuotation partOf;
    private Product contains;


    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = super.getProperties();
        properties.put("quantity",quantity);
        properties.put("salesPrice",salesPrice);
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    public BigDecimal getPurchPrice() {
        return purchPrice;
    }

    public void setPurchPrice(BigDecimal purchPrice) {
        this.purchPrice = purchPrice;
    }

    public SalesQuotation getPartOf() {
        return partOf;
    }

    public void setPartOf(SalesQuotation partOf) {
        this.partOf = partOf;
    }

    public Product getContains() {
        return contains;
    }

    public void setContains(Product contains) {
        this.contains = contains;
    }
}
