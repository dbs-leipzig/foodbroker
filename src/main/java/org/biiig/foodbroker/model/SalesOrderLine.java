package org.biiig.foodbroker.model;

import java.math.BigDecimal;

/**
 * Created by peet on 21.11.14.
 */
public class SalesOrderLine extends AbstractERPOrderLine {

    private PurchOrderLine purchOrderLine;

    public BigDecimal getSalesPrice() {
        return (BigDecimal) this.properties.get("salesPrice");
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.properties.put("salesPrice",salesPrice);
    }

    public BigDecimal getSalesAmount() {
        return BigDecimal.valueOf(getQuantity()).multiply(getSalesPrice()).setScale(2,BigDecimal.ROUND_HALF_UP);
    }

    public SalesOrder getPartOf(){
        return (SalesOrder) getStartDataObject();
    }

    public PurchOrderLine getPurchOrderLine() {
        return purchOrderLine; 
    }

    public void setPurchOrderLine(PurchOrderLine purchOrderLine) {
        this.purchOrderLine = purchOrderLine;
    }
}
