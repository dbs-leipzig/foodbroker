package org.biiig.foodbroker.model;

/**
 * Created by peet on 20.11.14.
 */
public interface Relationship extends PropertyContainer {
    public DataObject getStartDataObject();
    public DataObject getEndDataObject();
    public String getStartAlias();
    public String getEndAlias();
}
