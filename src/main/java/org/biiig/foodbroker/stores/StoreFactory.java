package org.biiig.foodbroker.stores;

import org.biiig.foodbroker.formatter.Formatter;

/**
 * Created by peet on 27.11.14.
 */
public interface StoreFactory {
    Store newInstance(Formatter formatter);
}
