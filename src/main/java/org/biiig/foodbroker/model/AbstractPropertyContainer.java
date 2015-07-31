package org.biiig.foodbroker.model;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by peet on 20.11.14.
 */
public abstract class AbstractPropertyContainer implements PropertyContainer {
    protected final static AtomicLong instanceCount = new AtomicLong(0);
    protected final long id;
    protected final Map<String,Object> properties = new HashMap<>();
    protected final Map<String,String> metaData = new HashMap<>();

    public AbstractPropertyContainer() {
        this.id = instanceCount.addAndGet(1);
    }

    @Override
    public long getID() {
        return this.id;
    }

    @Override
    public Set<String> getPropertyKeys() {
        return properties.keySet();
    }

    @Override
    public Set<String> getMetaDataKeys() {
        return metaData.keySet();
    }

    @Override
    public Object getProperty(String key) {
        return properties.get(key);
    }

    @Override
    public String getMetaData(String key) {
        return metaData.get(key);
    }
}
