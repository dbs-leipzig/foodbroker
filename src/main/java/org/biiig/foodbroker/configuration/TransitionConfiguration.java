package org.biiig.foodbroker.configuration;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.biiig.foodbroker.model.MasterDataObject;

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
        float chance = baseValue;

        for(MasterDataObject object : influencingMasterDataObjects){
            if (object.getQuality() == "good"){
                if (higherIsBetter){
                    chance += influence;
                }
                else {
                    chance -= influence;
                }
            }
            else if (object.getQuality() == "bad"){
                if (higherIsBetter){
                    chance -= influence;
                }
                else {
                    chance += influence;
                }
            }
        }

        return value <= chance;
    }
}
