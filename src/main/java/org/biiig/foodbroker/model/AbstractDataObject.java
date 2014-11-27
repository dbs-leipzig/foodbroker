package org.biiig.foodbroker.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by peet on 12.11.14.
 */
public abstract class AbstractDataObject extends AbstractPropertyContainer implements DataObject{
    protected static AtomicLong instanceCount = new AtomicLong(0);
    protected final long id;
    protected Map<String,DataObject> nestedRelationships = new HashMap<>();

    public AbstractDataObject() {
        this.id = instanceCount.addAndGet(1);
        this.metaData.put("class",this.getClass().getSimpleName());
    }

    @Override
    public long getID() {
        return this.id;
    }

    @Override
    public Set<String> getNestedRelationshipKeys() {
        return nestedRelationships.keySet();
    }

    @Override
    public SimpleRelationship getNestedRelationship(String key) {
        return new SimpleRelationship(this,this.nestedRelationships.get(key),key);
    }
}
