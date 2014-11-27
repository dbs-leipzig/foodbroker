package org.biiig.foodbroker.model;


import java.util.Date;

/**
 * Created by peet on 18.11.14.
 */
public abstract class AbstractERPTransactionalDataObject extends AbstractERPDataObject implements TransactionalDataObject {
    public void setDate(Date date) {
        this.properties.put("date",date);
        this.metaData.put("kind","TransData");
    }

    public Date getDate() {
        return (Date) this.properties.get("date");
    }
}
