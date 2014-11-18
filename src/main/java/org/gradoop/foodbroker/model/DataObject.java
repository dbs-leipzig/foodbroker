package org.gradoop.foodbroker.model;

import java.util.Map;

/**
 * Created by peet on 18.11.14.
 */
public interface DataObject {
    /**
     * Returns all properties of a data object.
     *
     * @return property map
     */
    public Map<String,Object> getProperties();

    /**
     * Return a global unique identifier.
     *
     * @return
     */
    public long getID();
    public String getLabel();
}
