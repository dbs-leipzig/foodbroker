package org.biiig.foodbroker.stores;

import org.biiig.foodbroker.formatter.Formatter;
import org.biiig.foodbroker.model.DataObject;
import org.biiig.foodbroker.model.Relationship;

/**
 * Created by peet on 18.11.14.
 */
public class ConsoleStore extends AbstractStore {

    public ConsoleStore(Formatter formatter){
        this.formatter = formatter;
    }



    @Override
    public void store(DataObject dataObject) {
        System.out.println(formatter.format(dataObject));

        if (formatter.hasSeparateRelationshipHandling()) {
            for (String key : dataObject.getNestedRelationshipKeys()) {
                store(dataObject.getNestedRelationship(key));
            }
        }

    }

    @Override
    public void store(Relationship relationship) {
        System.out.println(formatter.format(relationship));
    }

}
