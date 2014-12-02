package org.biiig.foodbroker.model;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by peet on 27.11.14.
 */
public abstract class AbstractRelationship extends AbstractPropertyContainer implements Relationship {

    protected static AtomicLong instanceCount = new AtomicLong(0);

    protected DataObject startDataObject;
    protected DataObject endDataObject;

    public AbstractRelationship() {
        instanceCount.addAndGet(1);
    }

    public DataObject getStartDataObject() {
        return startDataObject;
    }

    public void setStartDataObject(DataObject startDataObject) {
        this.startDataObject = startDataObject;
    }

    public DataObject getEndDataObject() {
        return endDataObject;
    }

    public void setEndDataObject(DataObject endDataObject) {
        this.endDataObject = endDataObject;
    }

    public static long getInstanceCount() {
        return instanceCount.longValue();
    }
}
