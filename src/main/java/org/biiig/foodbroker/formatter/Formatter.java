package org.biiig.foodbroker.formatter;

import org.biiig.foodbroker.model.DataObject;
import org.biiig.foodbroker.model.Relationship;

/**
 *
 * Created by peet on 18.11.14.
 */
public interface Formatter {
    /**
     * Creates a string representation of a data object.
     *
     * @return string representation
     */
    String format (DataObject dataObject);
    String format (Relationship relationship);
    String getFileExtension();
    String getNodeFilePath();
    String getEdgeFilePath();
    String getNodeFilePath(int thread);
    String getEdgeFilePath(int thread);
    String getDataFilePath();
    boolean hasSeparateRelationshipHandling();

}
