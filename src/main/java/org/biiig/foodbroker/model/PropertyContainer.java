package org.biiig.foodbroker.model;
import java.util.Set;

/**
 * Created by peet on 20.11.14.
 */
public interface PropertyContainer {
    public Set<String> getPropertyKeys();
    public Set<String> getMetaDataKeys();
    public Object getProperty(String key);
    public String getMetaData(String key);
}
