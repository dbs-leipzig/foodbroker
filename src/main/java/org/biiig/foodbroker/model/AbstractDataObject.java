package org.biiig.foodbroker.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by peet on 12.11.14.
 */
public abstract class AbstractDataObject extends AbstractPropertyContainer implements DataObject{
    protected Map<String,DataObject> nestedRelationships = new HashMap<>();

    public AbstractDataObject() {
        this.metaData.put("label",this.getClass().getSimpleName());
    }

    @Override
    public Set<String> getNestedRelationshipKeys() {
        return nestedRelationships.keySet();
    }

    @Override
    public SimpleRelationship getNestedRelationship(String key) {
        return new SimpleRelationship(this,this.nestedRelationships.get(key),key);
    }

    public static long getInstanceCount() {
        return instanceCount.longValue();
    }
}
