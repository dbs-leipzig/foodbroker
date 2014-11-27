package org.biiig.foodbroker.model;

import java.util.Map;

/**
 * Created by peet on 14.11.14.
 */
public abstract class AbstractERPMasterDataObject extends AbstractERPDataObject implements MasterDataObject {

    public AbstractERPMasterDataObject(Map<String, Object> baseValues) {
        super();
        this.metaData.put("quality",(String) baseValues.get("_quality"));
        this.metaData.put("kind","MasterData");
    }

    public String getQuality() {
        return this.metaData.get("quality");
    }
}
