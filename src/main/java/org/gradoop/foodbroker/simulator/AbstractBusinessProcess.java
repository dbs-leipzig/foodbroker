package org.gradoop.foodbroker.simulator;

import org.gradoop.foodbroker.generator.MasterDataGenerator;
import org.gradoop.foodbroker.manager.DateManager;
import org.gradoop.foodbroker.model.MasterDataObject;
import org.gradoop.foodbroker.model.TransactionalDataObject;
import org.gradoop.foodbroker.simulator.BusinessProcess;
import org.gradoop.foodbroker.stores.Store;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by peet on 20.11.14.
 */
public class AbstractBusinessProcess implements BusinessProcess {
    @Override
    public void start(Date startDate) {

    }

    @Override
    public List<TransactionalDataObject> getTransactionalDataObjects() {
        return null;
    }

}
