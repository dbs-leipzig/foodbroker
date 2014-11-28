package org.biiig.foodbroker.model;

import java.util.Set;

/**
 * Created by peet on 18.11.14.
 */
public interface DataObject extends PropertyContainer{
    /**
     * Return a global unique identifier.
     *
     * @return
     */
    long getID();

    public Set<String> getNestedRelationshipKeys();
    public SimpleRelationship getNestedRelationship(String key);
    public Object getLocalID();
}
