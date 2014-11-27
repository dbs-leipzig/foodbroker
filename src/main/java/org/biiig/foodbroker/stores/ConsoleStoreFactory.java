package org.biiig.foodbroker.stores;

import org.biiig.foodbroker.formatter.Formatter;

/**
 * Created by peet on 27.11.14.
 */
public class ConsoleStoreFactory extends AbstractStoreFactory {
    @Override
    public Store newInstance(Formatter formatter) {
        return new ConsoleStore(formatter);
    }
}
