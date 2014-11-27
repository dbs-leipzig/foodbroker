package org.biiig.foodbroker.model;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by peet on 13.11.14.
 */
public class Product extends AbstractERPMasterDataObject {

    public Product(Map<String, Object> baseValues) {
        super(baseValues);
        this.setNum("PRD", 10);
        this.properties.put("name",baseValues.get("name"));
        this.properties.put("category",baseValues.get("category"));
        this.setPrice();
    }

    private void setPrice() {
        PropertiesConfiguration config = null;
        try {
            config = new PropertiesConfiguration("config.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        float minPrice = config.getFloat("ProductMinPrice");
        float maxPrice = config.getFloat("ProductMaxPrice");

        BigDecimal price = BigDecimal.valueOf(
                minPrice + (float) (Math.random() * ((1 + maxPrice) - minPrice))
        ).setScale(2, BigDecimal.ROUND_HALF_UP);

        this.properties.put("price",price);
    }

    public BigDecimal getPrice() {
        return (BigDecimal) this.properties.get("price");
    }

}
