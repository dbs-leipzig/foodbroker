package org.gradoop.foodbroker.simulator;

import org.gradoop.foodbroker.configuration.ProcessConfiguration;
import org.gradoop.foodbroker.factory.DateManager;
import org.gradoop.foodbroker.stores.Store;

/**
 * Created by peet on 25.11.14.
 */
public class BusinessProcessRunner implements Runnable {

    private BusinessProcess businessProcess;
    private DateManager dateManager;
    private int cases;

    public BusinessProcessRunner(BusinessProcess businessProcess, int scaleFactor, int availableProcessors ){

        this.businessProcess = businessProcess;
        ProcessConfiguration config = businessProcess.getConfig();
        this.cases = config.getCases(scaleFactor) / availableProcessors;
        this.dateManager = new DateManager( config.getStartDate(),config.getEndDate(), this.cases);
    }

    @Override
    public void run() {
        businessProcess.getStore().open();
        for (int cas=1; cas<=cases; cas++){
            businessProcess.start(dateManager.getStartDate(cas));
        }
        businessProcess.getStore().close();
    }
}
