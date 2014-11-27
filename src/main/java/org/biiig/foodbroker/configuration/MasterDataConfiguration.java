package org.biiig.foodbroker.configuration;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Created by peet on 13.11.14.
 */
public class MasterDataConfiguration {

    private int amount;
    private int lastGoodOne;
    private int firstBadOne;

    public MasterDataConfiguration(String className, int scaleFactor) {
        PropertiesConfiguration config = null;
        try {
            config = new PropertiesConfiguration("config.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        this.amount = config.getInt(className + "Offset", 1) + config.getInt(className + "Growth", 0) * scaleFactor;

        float goodRatio = config.getFloat(className + "GoodRatio", 0.0f);
        float badRatio = config.getFloat(className + "BadRatio", 0.0f);

        this.lastGoodOne = (int) (amount * goodRatio);
        this.firstBadOne = (int) (amount * (1 - badRatio));
    }

    public int getAmount() {
        return amount;
    }

    public int getLastGoodOne() {
        return lastGoodOne;
    }

    public int getFirstBadOne() {
        return firstBadOne;
    }

}
