package org.gradoop.foodbroker.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by peet on 20.11.14.
 */
public abstract class AbstractPropertyContainer implements PropertyContainer {
    public Map<String, Object> getProperties() {
        return new HashMap();
    }
}
