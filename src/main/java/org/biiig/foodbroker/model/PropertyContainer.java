package org.biiig.foodbroker.model;
import java.util.Set;
import java.util.UUID;

/**
 * Created by peet on 20.11.14.
 */
public interface PropertyContainer {
    /**
     * Return a global unique identifier.
     *
     * @return
     */
    UUID getID();

    public Set<String> getPropertyKeys();
    public Set<String> getMetaDataKeys();
    public Object getProperty(String key);
    public String getMetaData(String key);
}
