package org.gradoop.foodbroker.model;

/**
 * Created by peet on 20.11.14.
 */
public class SimpleRelationship implements Relationship {

    private final String label;
    private final DataObject startDataObject;
    private final DataObject endDataObject;

    public SimpleRelationship(String label, DataObject startDataObject, DataObject endDataObject){
        this.label = label;
        this.startDataObject = startDataObject;
        this.endDataObject = endDataObject;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public DataObject getStartDataObject() {
        return startDataObject;
    }

    @Override
    public DataObject getEndDataObject() {
        return endDataObject;
    }
}
