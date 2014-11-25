package org.gradoop.foodbroker.model;

/**
 * Created by peet on 20.11.14.
 */
public interface Relationship {
    public String getLabel();
    public DataObject getStartDataObject();
    public DataObject getEndDataObject();
}
