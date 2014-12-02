package org.biiig.foodbroker.model;

/**
 * Created by peet on 02.12.14.
 */
public class User extends AbstractCITDataObject implements MasterDataObject{

    public User(Employee employee) {
        this.properties.put("name",employee.getProperty("name"));
        this.properties.put("erpEmplNum", employee.getProperty("num"));
        this.metaData.put("quality",employee.getQuality());
        this.metaData.put("kind","MasterData");
        setEmail();
    }

    private void setEmail() {
        String email = (String) getProperty("name");
        email = email.replace(" ",".").toLowerCase();
        email += "@biiig.org";
        this.properties.put("email",email);
    }

    @Override
    public Object getLocalID() {
        return getProperty("email");
    }

    @Override
    public String getQuality() {
        return this.metaData.get("quality");
    }
}
