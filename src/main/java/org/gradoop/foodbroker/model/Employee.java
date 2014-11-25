package org.gradoop.foodbroker.model;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by peet on 12.11.14.
 */
public class Employee extends AbstractMasterDataObject {

    private String name;
    private String num;
    private String gender;

    public Employee(Map<String, Object> baseValues) {
        super(baseValues);
        this.name = baseValues.get("first_name") + " " + baseValues.get("family_name");
        this.gender = (String) baseValues.get("gender");
        this.num = getBusinessKey("EMP", 10);
    }

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = super.getProperties();
        properties.put("name",name);
        properties.put("num",num);
        properties.put("gender",gender);
        return properties;
    }

    public String getNum() {
        return num;
    }
}
