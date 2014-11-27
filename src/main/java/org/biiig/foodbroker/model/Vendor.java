package org.biiig.foodbroker.model;

import java.util.Map;

/**
 * Created by peet on 13.11.14.
 */
public class Vendor extends AbstractERPMasterDataObject {

    public Vendor(Map<String, Object> baseValues) {
        super(baseValues);
        this.setNum("VEN", 10);
        this.properties.put("name",baseValues.get("name"));
    }
}