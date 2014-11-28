package org.biiig.foodbroker.formatter;

/**
 * Created by peet on 28.11.14.
 */
public class SQLFormatterFactory extends AbstractFormatterFactory{
    @Override
    public Formatter newInstance() {
        return new SQLFormatter();
    }
}
