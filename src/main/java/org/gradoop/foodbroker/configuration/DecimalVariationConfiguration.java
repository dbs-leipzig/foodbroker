package org.gradoop.foodbroker.configuration;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.gradoop.foodbroker.model.MasterDataObject;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by peet on 21.11.14.
 */
public class DecimalVariationConfiguration {
    private float baseValue;
    private float influence;
    private boolean higherIsBetter;

    public DecimalVariationConfiguration(String parameter){
        try {
            PropertiesConfiguration config = new PropertiesConfiguration("config.properties");
            baseValue = config.getFloat(parameter + "Base");
            influence = config.getFloat(parameter +"Influence");
            higherIsBetter = config.getBoolean(parameter + "HigherIsBetter");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public BigDecimal getValue(List<MasterDataObject> influencingMasterDataObjects){
        float value = baseValue;

        for(MasterDataObject object : influencingMasterDataObjects){
            if (object.getQuality() == "good"){
                if(higherIsBetter){
                    value += influence;
                }
                else{
                    value -= influence;
                }
            }
            else if (object.getQuality() == "bad"){
                if(higherIsBetter){
                    value -= influence;
                }
                else{
                    value += influence;
                }
            }
        }

        return BigDecimal.valueOf(value).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

}
