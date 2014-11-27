package org.gradoop.foodbroker.simulation;

import org.gradoop.foodbroker.configuration.ProcessConfiguration;
import org.gradoop.foodbroker.model.TransactionalDataObject;
import org.gradoop.foodbroker.stores.Store;

import java.util.Date;
import java.util.List;

/**
 * Created by peet on 18.11.14.
 */
public interface BusinessProcess {
    void start(Date startDate);
    List<TransactionalDataObject> getTransactionalDataObjects();
    ProcessConfiguration getConfig();
    Store getStore();
}
