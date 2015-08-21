package org.biiig.foodbroker.model;

/**
 * Created by peet on 27.11.14.
 */
public abstract class AbstractERPDataObject extends AbstractDataObject {

    public AbstractERPDataObject() {
        super();
        this.metaData.put("system","ERP");
    }

    protected void setNum(String prefix, int length) {
        String idString = String.valueOf(id);
        int fillCharCount = length - idString.length() - prefix.length();
        StringBuilder propertyStringBuilder = new StringBuilder(prefix);
        for (int i = 1; i <= fillCharCount; i++) {
            propertyStringBuilder.append("0");
        }

        this.properties.put("num",propertyStringBuilder.append(idString).toString());
    }

    @Override
    public Object getLocalID() {
        return this.properties.get("num");
    }


}
