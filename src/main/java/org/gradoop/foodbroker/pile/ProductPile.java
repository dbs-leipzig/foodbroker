package org.gradoop.foodbroker.pile;

import org.gradoop.foodbroker.model.MasterDataObject;
import org.gradoop.foodbroker.model.Product;

import java.util.List;

/**
 * Created by peet on 25.11.14.
 */
public class ProductPile extends AbstractMasterDataPile {

    private final List<Product> products;

    public ProductPile(List<Product> products){
        super(products.size());
        this.products = products;
    }

    @Override
    public Product nextInstance() {
        return products.get(nextId());
    }
}
