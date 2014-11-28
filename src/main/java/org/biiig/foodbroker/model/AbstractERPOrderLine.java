package org.biiig.foodbroker.model;

/**
 * Created by peet on 27.11.14.
 */
public abstract class AbstractERPOrderLine extends AbstractRelationship {

    public AbstractERPOrderLine() {
        this.metaData.put("type",this.getClass().getSimpleName());
        this.metaData.put("system","ERP");
    }

    public int getQuantity() {
        return (int) this.properties.get("quantity");
    }

    public void setQuantity(int quantity) {
        this.properties.put("quantity",quantity);
    }

    public Product getContains() {
        return (Product) this.getEndDataObject();
    }

    public void setContains(Product contains) {
        this.setEndDataObject(contains);
    }

    public void setPartOf(TransactionalDataObject partOf) {
        this.setStartDataObject(partOf);
    }

    @Override
    public String getStartAlias() {
        return "partOf";
    }

    @Override
    public String getEndAlias() {
        return "contains";
    }
}
