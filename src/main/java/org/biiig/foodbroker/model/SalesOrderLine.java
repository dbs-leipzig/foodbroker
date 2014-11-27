package org.biiig.foodbroker.model;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by peet on 21.11.14.
 */
public class SalesOrderLine extends AbstractOrderLine {

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
}
