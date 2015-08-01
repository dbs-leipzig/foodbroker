package org.biiig.foodbroker.model;

/**
 * Created by peet on 20.11.14.
 */
public class SimpleRelationship extends AbstractRelationship {

    public SimpleRelationship(DataObject startDataObject, DataObject endDataObject,String type){
        super();
        this.startDataObject = startDataObject;
        this.endDataObject = endDataObject;
        this.metaData.put("label",type);
    }

    @Override
    public String getStartAlias() {
        return "start";
    }

    @Override
    public String getEndAlias() {
        return "end";
    }
}
