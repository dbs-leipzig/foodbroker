package org.biiig.foodbroker.model;

import org.biiig.foodbroker.pile.ProductPile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by peet on 14.11.14.
 */
public class ProductFactory extends AbstractMasterDataFactory {

    private final List<Product> products = new ArrayList<>();

    @Override
    public Product newInstance(Map<String, Object> baseValues) {
        Product product = new Product(baseValues);
        this.products.add(product);
        return product;
    }

    public ProductPile newProductPile(){
        return new ProductPile(products);
    }

    @Override
    public String getInstanceClassName() {
        return Product.class.getSimpleName();
    }

    @Override
    public String getRowCountSql() {
        return "WITH products AS(\n" +
                "  SELECT 'fruits' AS category, name FROM fruits\n" +
                "  UNION ALL\n" +
                "  SELECT 'vegetables' AS category, name FROM vegetables  \n" +
                "  UNION ALL\n" +
                "  SELECT 'nuts' AS category, name FROM nuts  \n" +
                ")\n" +
                "SELECT\n" +
                "  COUNT(*)" +
                "FROM products p\n" +
                "JOIN product_adjectives a1\n" +
                "JOIN product_adjectives a2\n" +
                "ON a1.name <> a2.name;";
    }

    @Override
    public String getBaseValueSql() {
        return "WITH products AS(\n" +
                "  SELECT 'fruits' AS category, name FROM fruits\n" +
                "  UNION ALL\n" +
                "  SELECT 'vegetables' AS category, name FROM vegetables  \n" +
                "  UNION ALL\n" +
                "  SELECT 'nuts' AS category, name FROM nuts  \n" +
                ")\n" +
                "SELECT\n" +
                "  a1.name || ' ' || a2.name || ' ' || p.name AS name, category\n" +
                "FROM products p\n" +
                "JOIN product_adjectives a1\n" +
                "JOIN product_adjectives a2\n" +
                "ON a1.name <> a2.name\n" +
                "ORDER BY p.name,a1.name,a2.name;";
    }
}
