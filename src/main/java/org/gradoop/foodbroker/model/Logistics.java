package org.gradoop.foodbroker.model;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by peet on 13.11.14.
 */
public class Logistics extends AbstractMasterDataObject {

    private String name;
    private String num;

    public Logistics(Map<String, Object> baseValues) {
        super(baseValues);
        this.name = (String) baseValues.get("name");
        this.num = getBusinessKey("LOG", 10);
    }

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = super.getProperties();
        properties.put("name", name);
        properties.put("num", num);
        return properties;
    }
}