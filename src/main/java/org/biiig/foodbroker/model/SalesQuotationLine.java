package org.biiig.foodbroker.model;

import java.math.BigDecimal;

/**
 * Created by peet on 20.11.14.
 */
public class SalesQuotationLine extends AbstractERPOrderLine {

    public BigDecimal getSalesPrice() {
        return (BigDecimal) this.properties.get("salesPrice");
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.properties.put("salesPrice",salesPrice);
    }

    public BigDecimal getPurchPrice() {
        return (BigDecimal) this.properties.get("purchPrice");
    }

    public void setPurchPrice(BigDecimal purchPrice) {
        this.properties.put("purchPrice",purchPrice);
    }
}
