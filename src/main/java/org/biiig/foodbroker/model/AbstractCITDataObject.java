package org.biiig.foodbroker.model;

/**
 * Created by peet on 02.12.14.
 */
public abstract class AbstractCITDataObject extends AbstractDataObject {
    public AbstractCITDataObject(){
        super();
        this.metaData.put("system","CIT");
    }
}
