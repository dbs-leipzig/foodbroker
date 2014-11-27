package org.gradoop.foodbroker.stores;

import org.gradoop.foodbroker.formatter.Formatter;

/**
 * Created by peet on 27.11.14.
 */
public interface StoreFactory {
    Store newInstance(Formatter formatter);
}
