package org.biiig.foodbroker.simulation;

import org.biiig.foodbroker.configuration.ProcessConfiguration;
import org.biiig.foodbroker.model.TransactionalDataObject;
import org.biiig.foodbroker.stores.Store;

import java.util.Date;
import java.util.List;

/**
 * Created by peet on 18.11.14.
 */
public interface BusinessProcess {
    void start(Date startDate);
    ProcessConfiguration getConfig();
    Store getStore();
}
