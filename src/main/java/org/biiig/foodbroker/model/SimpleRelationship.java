package org.biiig.foodbroker.model;

/**
 * Created by peet on 20.11.14.
 */
public class SimpleRelationship extends AbstractRelationship {


    public SimpleRelationship(DataObject startDataObject, DataObject endDataObject,String type){
        this.startDataObject = startDataObject;
        this.endDataObject = endDataObject;
        this.metaData.put("type",type);
    }

}
