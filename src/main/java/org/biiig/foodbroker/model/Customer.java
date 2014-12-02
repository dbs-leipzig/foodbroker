package org.biiig.foodbroker.model;

import java.util.Map;

/**
 * Created by peet on 13.11.14.
 */
public class Customer extends AbstractERPMasterDataObject {

    private Client client;

    public Customer(Map<String, Object> baseValues) {
        super(baseValues);
        this.setNum("CUS", 10);
        this.properties.put("name",baseValues.get("name"));
    }

    public Client getClient() {
        return client;
    }

    public boolean hasClient() {
        return client != null;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}