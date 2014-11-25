package org.gradoop.foodbroker.model;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by peet on 13.11.14.
 */
public class Product extends AbstractMasterDataObject {

    private String name;
    private String num;
    private String category;
    private BigDecimal price;

    public Product(Map<String, Object> baseValues) {
        super(baseValues);

        this.name = (String) baseValues.get("name");
        this.num = getBusinessKey("PRD", 10);
        this.category = (String) baseValues.get("category");

        PropertiesConfiguration config = null;
        try {
            config = new PropertiesConfiguration("config.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        float minPrice = config.getFloat("ProductMinPrice");
        float maxPrice = config.getFloat("ProductMaxPrice");

        this.price = BigDecimal.valueOf(
                minPrice + (float) (Math.random() * ((1 + maxPrice) - minPrice))
        ).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = super.getProperties();
        properties.put("name",name);
        properties.put("num",num);
        properties.put("category", category);
        properties.put("price",price);
        return properties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
