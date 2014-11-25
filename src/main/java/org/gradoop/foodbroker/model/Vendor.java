package org.gradoop.foodbroker.model;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by peet on 13.11.14.
 */
public class Vendor extends AbstractMasterDataObject {

    private String name;
    private String num;

    public Vendor(Map<String, Object> baseValues) {
        super(baseValues);
        this.name = (String) baseValues.get("name");
        this.num = getBusinessKey("VEN", 10);
    }

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = super.getProperties();
        properties.put("name", name);
        properties.put("num", num);
        return properties;
    }

    public String getNum() {
        return num;
    }
}