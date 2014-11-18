package org.gradoop.foodbroker.stores;

import org.gradoop.foodbroker.formatters.Formatter;
import org.gradoop.foodbroker.model.DataObject;

/**
 * Created by peet on 18.11.14.
 */
public class ConsoleStore implements Store {

    private Formatter formatter;

    public ConsoleStore(Formatter formatter){
        this.formatter = formatter;
    }

    @Override
    public void open() {

    }

    @Override
    public void store(DataObject dataObject) {
        System.out.println(formatter.format(dataObject));
    }

    @Override
    public void close() {

    }
}
