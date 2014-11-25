package org.gradoop.foodbroker.manager;

import org.gradoop.foodbroker.model.MasterDataObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by peet on 18.11.14.
 */
public abstract class AbstractMasterDataManager implements MasterDataManager{

    protected List<MasterDataObject> instances = new ArrayList<>();
    protected int nextInstanceID = -1;

    @Override
    public void shuffle() {
        Collections.shuffle(instances);
    }

    @Override
    public MasterDataObject nextInstance() {
        if (nextInstanceID >= instances.size()-1) {
            nextInstanceID = 0;
        }
        else{
            nextInstanceID++;
        }
        return instances.get(nextInstanceID);
    }
}
