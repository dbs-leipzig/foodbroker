package org.biiig.foodbroker.formatter;

/**
 * Created by peet on 27.11.14.
 */
public interface FormatterFactory {
    Formatter newInstance(String directory);
}
