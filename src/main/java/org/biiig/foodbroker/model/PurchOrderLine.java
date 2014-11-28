package org.biiig.foodbroker.model;

import java.math.BigDecimal;

/**
 * Created by peet on 21.11.14.
 */
public class PurchOrderLine extends AbstractERPOrderLine {

    public BigDecimal getPurchPrice() {
        return (BigDecimal) this.properties.get("purchPrice");
    }

    public void setPurchPrice(BigDecimal purchPrice) {
        this.properties.put("purchPrice",purchPrice);
    }

    public BigDecimal getPurchAmount(){
        return BigDecimal.valueOf(getQuantity()).multiply(getPurchPrice());
    }

    public PurchOrder getPartOf(){
        return (PurchOrder) getStartDataObject();
    }
}
