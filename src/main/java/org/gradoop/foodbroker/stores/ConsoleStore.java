package org.gradoop.foodbroker.stores;

import org.gradoop.foodbroker.formatter.Formatter;
import org.gradoop.foodbroker.model.DataObject;
import org.gradoop.foodbroker.model.Relationship;
import org.gradoop.foodbroker.model.SimpleRelationship;

import javax.xml.crypto.Data;
import java.util.Map;

/**
 * Created by peet on 18.11.14.
 */
public class ConsoleStore implements Store {

    private Formatter formatter;

    public ConsoleStore(Formatter formatter){
        this.formatter = formatter;
    }

    @Override
    public void open() {

    }

    @Override
    public void store(DataObject dataObject) {
        System.out.println(formatter.format(dataObject));
        for(Map.Entry<String,Object> property : dataObject.getProperties().entrySet()){
            Object value = property.getValue();
            if(value instanceof DataObject){
                Relationship relationship = new SimpleRelationship(
                    property.getKey(),
                    dataObject,
                    (DataObject) value
                );
                store(relationship);
            }
        }
    }

    @Override
    public void store(Relationship relationship) {
        System.out.println(formatter.format(relationship));
    }


    @Override
    public void close() {

    }
}
