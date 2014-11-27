package org.biiig.foodbroker.pile;

import org.biiig.foodbroker.model.Logistics;

import java.util.List;

/**
 * Created by peet on 25.11.14.
 */
public class LogisticsPile extends AbstractMasterDataPile {

    private final List<Logistics> logisticses;

    public LogisticsPile(List<Logistics> logisticses){
        super(logisticses.size());
        this.logisticses = logisticses;
    }

    @Override
    public Logistics nextInstance() {
        return logisticses.get(nextId());
    }
}
