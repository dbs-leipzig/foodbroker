package org.biiig.foodbroker.formatter;

import org.biiig.foodbroker.model.DataObject;
import org.biiig.foodbroker.model.PropertyContainer;
import org.biiig.foodbroker.model.Relationship;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by peet on 18.11.14.
 */
public class JSONFormatter extends AbstractFormatter {

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat( "yyyy-MM-dd");

    @Override
    public String getFileExtension() {
        return ".json";
    }

    public String format (DataObject dataObject){
        JSONObject json = new JSONObject();

        json.put("id", dataObject.getID());
        json.put("data", getProperties(dataObject));
        json.put("meta", getMetaData(dataObject));

        return json.toString();
    }

    @Override
    public String format(Relationship relationship) {
        JSONObject json = new JSONObject();

        json.put("start",relationship.getStartDataObject().getID());
        json.put("end",relationship.getEndDataObject().getID());
        json.put("data", getProperties(relationship));
        json.put("meta", getMetaData(relationship));

        return json.toString();
    }

    private JSONObject getProperties(PropertyContainer propertyContainer){
        JSONObject json = new JSONObject();

        for(String key : propertyContainer.getPropertyKeys()){
            Object value = propertyContainer.getProperty(key);

            if (value instanceof Date) {
                value = dateFormatter.format((Date) value);
            }
            json.put(key, value);
        }
        return json;
    }

    private JSONObject getMetaData(PropertyContainer propertyContainer){
        JSONObject json = new JSONObject();

        for(String key : propertyContainer.getMetaDataKeys()){
            String value = propertyContainer.getMetaData(key);
            json.put(key,value);
        }
        return json;
    }

    protected String getExtension(){
        return ".json";
    }

    @Override
    public boolean hasSeparateRelationshipHandling() {
        return true;
    }
}
