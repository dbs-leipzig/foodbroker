package org.gradoop.foodbroker.pile;

import org.gradoop.foodbroker.model.MasterDataObject;

/**
 * Created by peet on 25.11.14.
 */
public interface MasterDataPile {
    MasterDataObject nextInstance();
}
