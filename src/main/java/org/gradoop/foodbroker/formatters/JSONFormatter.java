package org.gradoop.foodbroker.formatters;

import org.gradoop.foodbroker.model.DataObject;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by peet on 18.11.14.
 */
public class JSONFormatter implements Formatter {
    public String format (DataObject dataObject){
        JSONObject json = new JSONObject();
        json.put("id", dataObject.getID());
        json.put("label",dataObject.getLabel());

        JSONObject data = new JSONObject();
        data.put("_CLASS", dataObject.getClass().getSimpleName());

        for(Map.Entry<String,Object> property : dataObject.getProperties().entrySet()){
            data.put(property.getKey(),property.getValue());
        }

        json.put("data", data);
        return json.toString();
    }
}
