package org.gradoop.foodbroker.model;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by peet on 12.11.14.
 */
public abstract class AbstractDataObject implements DataObject{
    protected static long id = 0;

    public AbstractDataObject() {
        id++;
    }

    protected String getBusinessKey(String prefix, int length) {
        String idString = String.valueOf(id);
        int fillCharCount = length - idString.length() - prefix.length();

        for (int i = 1; i <= fillCharCount; i++) {
            prefix += "0";
        }

        return prefix + idString;

    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ":" + String.valueOf(this.id);
    }

    @Override
    public long getID() {
        return this.id;
    }

    @Override
    public String getLabel() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap();
        properties.put("_CLASS", this.getClass().getSimpleName());
        return properties;
    }
}
