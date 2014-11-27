package org.biiig.foodbroker.configuration;

import org.biiig.foodbroker.model.MasterDataObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by peet on 21.11.14.
 */
public class DelayConfiguration extends IntRangeConfiguration {

    public DelayConfiguration (String parameter){
        super(parameter);
    }

    public Date delay (Date date, List<MasterDataObject> influencingMasterDataObjects){
        int delay = getValue(influencingMasterDataObjects);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,delay);

        return calendar.getTime();
    }

    public Date delay (Date date, MasterDataObject influencingMasterDataObject) {
        List <MasterDataObject> influencingMasterDataObjects = new ArrayList<>();
        influencingMasterDataObjects.add(influencingMasterDataObject);
        return delay(date,influencingMasterDataObjects);
    }

}
