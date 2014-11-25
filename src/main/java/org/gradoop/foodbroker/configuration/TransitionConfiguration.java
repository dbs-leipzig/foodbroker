package org.gradoop.foodbroker.configuration;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.gradoop.foodbroker.model.MasterDataObject;

import java.util.List;

/**
 * Created by peet on 21.11.14.
 */
public class TransitionConfiguration {

    private float baseValue;
    private float influence;
    private boolean higherIsBetter;

    public TransitionConfiguration(String parameter) {
        try {
            PropertiesConfiguration config = new PropertiesConfiguration("config.properties");
            baseValue = config.getFloat(parameter + "Base");
            influence = config.getFloat(parameter + "Influence");
            higherIsBetter = config.getBoolean(parameter + "HigherIsBetter");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public boolean happens (List<MasterDataObject> influencingMasterDataObjects ){
        float value = (float) Math.random();

        for(MasterDataObject object : influencingMasterDataObjects){
            if (object.getQuality() == "good"){
                if (higherIsBetter){
                    value += influence;
                }
                else {
                    value -= influence;
                }
            }
            else if (object.getQuality() == "bad"){
                if (higherIsBetter){
                    value -= influence;
                }
                else {
                    value += influence;
                }
            }
        }

        if(value < 0){
            value = 0.0f;
        }
        else if (value > 1 ){
            value = 1.0f;
        }

        return value >= baseValue;
    }
}
