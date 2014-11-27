package org.gradoop.foodbroker.formatter;

/**
 * Created by peet on 27.11.14.
 */
public class JSONFormatterFactory extends AbstractFormatterFactory {
    @Override
    public Formatter newInstance() {
        return new JSONFormatter();
    }
}
