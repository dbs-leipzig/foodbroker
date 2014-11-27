package org.biiig.foodbroker.model;

import java.util.Map;

/**
 * Created by peet on 13.11.14.
 */
public class Logistics extends AbstractERPMasterDataObject {

    public Logistics(Map<String, Object> baseValues) {
        super(baseValues);
        this.setNum("LOG", 10);
        this.properties.put("name",baseValues.get("name"));
    }

}