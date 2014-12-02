package org.biiig.foodbroker.model;

/**
 * Created by peet on 02.12.14.
 */
public class Client extends AbstractCITDataObject {

    public Client(Customer customer) {
        this.properties.put("name",customer.getProperty("name"));
        this.properties.put("contactPhone", "0123456789");
        this.properties.put("erpCustNum",customer.getProperty("num"));
        this.properties.put("account","CL" + String.valueOf(this.getID()));
        this.metaData.put("quality",customer.getQuality());
        this.metaData.put("kind","MasterData");
    }

    @Override
    public Object getLocalID() {
        return this.properties.get("account");
    }
}
