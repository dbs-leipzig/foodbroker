package org.biiig.foodbroker.model;

import java.util.Map;

/**
 * Created by peet on 12.11.14.
 */
public class Employee extends AbstractERPMasterDataObject {

    private User user;

    public Employee(Map<String, Object> baseValues) {
        super(baseValues);
        this.setNum("EMP", 10);
        this.properties.put("name",baseValues.get("first_name") + " " + baseValues.get("family_name"));
        this.properties.put("gender",baseValues.get("gender"));
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user ) {
        this.user = user;
    }
}
