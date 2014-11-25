package org.gradoop.foodbroker.formatter;

import org.gradoop.foodbroker.model.DataObject;
import org.gradoop.foodbroker.model.Relationship;

/**
 *
 * Created by peet on 18.11.14.
 */
public interface Formatter {
    /**
     * Creates a string representation of a data object.
     *
     * @param data object
     * @return string representation
     */
    String format (DataObject dataObject);
    String format (Relationship relationship);
    String getFileExtension();
}
