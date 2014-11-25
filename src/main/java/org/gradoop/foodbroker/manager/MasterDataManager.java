package org.gradoop.foodbroker.manager;

import org.gradoop.foodbroker.model.MasterDataObject;

import java.util.Map;

/**
 * Created by peet on 14.11.14.
 */
public interface MasterDataManager {
    public MasterDataObject newInstance(Map<String, Object> baseValues);

    public String getInstanceClassName();

    public String getRowCountSql();

    public String getBaseValueSql();

    public void shuffle();

    public MasterDataObject nextInstance();
}
