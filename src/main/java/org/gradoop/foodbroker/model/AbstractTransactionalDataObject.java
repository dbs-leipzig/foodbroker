package org.gradoop.foodbroker.model;

import java.util.Map;

/**
 * Created by peet on 18.11.14.
 */
public abstract class AbstractTransactionalDataObject extends AbstractDataObject implements TransactionalDataObject {
    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = super.getProperties();
        properties.put("_KIND","TransData");
        return properties;
    }
}
