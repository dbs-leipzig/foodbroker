package org.gradoop.foodbroker.model;

import java.util.List;
import java.util.Map;

/**
 * Created by peet on 18.11.14.
 */
public interface DataObject extends PropertyContainer{
    /**
     * Return a global unique identifier.
     *
     * @return
     */
    public long getID();
    public String getLabel();
    //public List<Relationship> getRelationships();
    //public void addRelationship(Relationship relationship);
}
