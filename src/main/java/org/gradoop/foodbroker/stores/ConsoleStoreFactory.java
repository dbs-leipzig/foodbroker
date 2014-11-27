package org.gradoop.foodbroker.stores;

import org.gradoop.foodbroker.formatter.Formatter;

/**
 * Created by peet on 27.11.14.
 */
public class ConsoleStoreFactory extends AbstractStoreFactory {
    @Override
    public Store newInstance(Formatter formatter) {
        return new ConsoleStore(formatter);
    }
}
