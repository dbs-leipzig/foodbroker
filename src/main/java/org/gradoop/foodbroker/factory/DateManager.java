package org.gradoop.foodbroker.factory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by peet on 18.11.14.
 */
public class DateManager {

    private List<Date> dates = new ArrayList<>();
    private final int divisor;

    public DateManager(Date startDate, Date endDate, int cases){

        SimpleDateFormat dateFormatter = new SimpleDateFormat( "E" );
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        while(calendar.getTime().before(endDate) || calendar.getTime().equals(endDate)){
            String dayName = dateFormatter.format(calendar.getTime());

            if(!dayName.startsWith("S")){
                this.dates.add(calendar.getTime());
            }

            calendar.add(Calendar.DATE,1);
        }

        if(dates.size() <= cases){
            divisor = cases / dates.size() + 1;
        }
        else {
            divisor = 1;
        }
    }

    public Date getStartDate(int kase){
        return dates.get(kase/divisor);
    }

}
