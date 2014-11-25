package org.gradoop.foodbroker.model;

import java.util.Map;

/**
 * Created by peet on 20.11.14.
 */
public interface PropertyContainer {
    /**
     * Returns all properties of a data object.
     *
     * @return property map
     */
    public Map<String,Object> getProperties();
}
