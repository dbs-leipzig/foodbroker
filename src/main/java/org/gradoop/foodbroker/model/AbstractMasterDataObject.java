package org.gradoop.foodbroker.model;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by peet on 14.11.14.
 */
public abstract class AbstractMasterDataObject extends AbstractDataObject implements MasterDataObject {

    protected String quality;

    public AbstractMasterDataObject(Map<String, Object> baseValues) {
        super();
        this.quality = String.valueOf(baseValues.get("_QC"));
    }

    @Override
    public String getLabel() {
        return this.getClass().getSimpleName() + ":" + String.valueOf(this.getID());
    }

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = super.getProperties();
        properties.put("_KIND","MasterData");
        properties.put("_QC",this.quality);
        return properties;
    }

    public String getQuality() {
        return quality;
    }
}
