package org.gradoop.foodbroker.simulator;

import org.gradoop.foodbroker.generator.MasterDataGenerator;
import org.gradoop.foodbroker.manager.DateManager;
import org.gradoop.foodbroker.manager.MasterDataManager;
import org.gradoop.foodbroker.model.TransactionalDataObject;
import org.gradoop.foodbroker.stores.Store;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by peet on 18.11.14.
 */
public interface BusinessProcess {
    void start(Date startDate);
    List<TransactionalDataObject> getTransactionalDataObjects();
}
