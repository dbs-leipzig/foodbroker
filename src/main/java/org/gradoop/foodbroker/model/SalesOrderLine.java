package org.gradoop.foodbroker.model;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by peet on 21.11.14.
 */
public class SalesOrderLine extends AbstractPropertyContainer implements Relationship {
    private int quantity;
    private BigDecimal salesPrice;
    private SalesOrder partOf;
    private Product contains;


    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = super.getProperties();
        properties.put("quantity",quantity);
        properties.put("salesPrice",salesPrice);
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

    public SalesOrder getPartOf() {
        return partOf;
    }

    public void setPartOf(SalesOrder partOf) {
        this.partOf = partOf;
    }

    public Product getContains() {
        return contains;
    }

    public void setContains(Product contains) {
        this.contains = contains;
    }

    public BigDecimal getSalesAmount() {
        return BigDecimal.valueOf(quantity).multiply(salesPrice).setScale(2,BigDecimal.ROUND_HALF_UP);
    }
}
