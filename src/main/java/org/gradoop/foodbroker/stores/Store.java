package org.gradoop.foodbroker.stores;

import org.gradoop.foodbroker.model.DataObject;

/**
 * Created by peet on 18.11.14.
 */
public interface Store {
    void open();
    void store(DataObject dataObject);
    void close();
}
