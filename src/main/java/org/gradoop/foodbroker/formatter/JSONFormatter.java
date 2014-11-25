package org.gradoop.foodbroker.formatter;

import org.gradoop.foodbroker.model.AbstractPropertyContainer;
import org.gradoop.foodbroker.model.DataObject;
import org.gradoop.foodbroker.model.PropertyContainer;
import org.gradoop.foodbroker.model.Relationship;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by peet on 18.11.14.
 */
public class JSONFormatter implements Formatter {

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat( "yyyy-MM-dd");

    @Override
    public String getFileExtension() {
        return ".json";
    }

    public String format (DataObject dataObject){
        JSONObject json = new JSONObject();
        json.put("id", dataObject.getID());
        json.put("label",dataObject.getLabel());

        JSONObject data = getData(dataObject);
        data.put("_CLASS", dataObject.getClass().getSimpleName());

        json.put("data", data);
        return json.toString();
    }

    @Override
    public String format(Relationship relationship) {
        JSONObject json = new JSONObject();

        json.put("label",relationship.getLabel());
        json.put("start",relationship.getStartDataObject().getID());
        json.put("end",relationship.getEndDataObject().getID());

        if(relationship instanceof PropertyContainer){
            AbstractPropertyContainer dataObject = (AbstractPropertyContainer) relationship;
            json.put("data", getData(dataObject));
        }


        return json.toString();
    }

    private JSONObject getData (PropertyContainer dataObject){
        JSONObject json = new JSONObject();

        for(Map.Entry<String,Object> property : dataObject.getProperties().entrySet()){
            Object value = property.getValue();

            if (! (value instanceof DataObject)){
                if (value instanceof Date) {
                    value = dateFormatter.format((Date) value);
                }
                json.put(property.getKey(), value);
            }
        }

        return json;
    }
}
