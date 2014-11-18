package org.gradoop.foodbroker.formatters;

import org.gradoop.foodbroker.model.DataObject;

/**
 * Created by peet on 18.11.14.
 */
public interface Formatter {
    String format (DataObject dataObject);
}
