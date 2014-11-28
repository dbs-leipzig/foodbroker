package org.biiig.foodbroker.stores;

/**
 * Created by peet on 28.11.14.
 */
public interface StoreCombiner {
    void add(Store store);
    void combine();
}
