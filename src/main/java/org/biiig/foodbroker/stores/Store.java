package org.biiig.foodbroker.stores;

import org.biiig.foodbroker.formatter.Formatter;
import org.biiig.foodbroker.model.DataObject;
import org.biiig.foodbroker.model.Relationship;

/**
 * Created by peet on 18.11.14.
 */
public interface Store {
    void open();
    void store(DataObject dataObject);
    void store(Relationship relationship);
    void close();
    Formatter getFormatter();
}
