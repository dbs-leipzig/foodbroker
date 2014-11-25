package org.gradoop.foodbroker.configuration;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.gradoop.foodbroker.model.MasterDataObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peet on 21.11.14.
 */
public class IntRangeConfiguration {

    private int min;
    private int max;
    private int influence;
    private boolean higherIsBetter;

    public IntRangeConfiguration (String parameter){
        try {
            PropertiesConfiguration config = new PropertiesConfiguration("config.properties");
            min = config.getInt(parameter+"Min");
            max = config.getInt(parameter+"Max");
            influence = config.getInt(parameter+"Influence",0);
            higherIsBetter = config.getBoolean(parameter+"HigherIsBetter",true);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

    }

    public int getValue (List<MasterDataObject> influencingMasterDataObjects){
        int value = 1 + (int)((double)(max - min) * Math.random()) + min;

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

        if(value < min){
            value = min;
        }
        else if (value > max ){
            value = max;
        }

        return value;
    }

    public int getValue (){
        return getValue(new ArrayList<MasterDataObject>());
    }

}
