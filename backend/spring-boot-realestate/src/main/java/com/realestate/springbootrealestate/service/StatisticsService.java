package com.realestate.springbootrealestate.service;

import com.realestate.springbootrealestate.model.Click;
import com.realestate.springbootrealestate.model.User;
import com.realestate.springbootrealestate.repository.ClickRepository;
import com.realestate.springbootrealestate.repository.PropertyRepository;
import com.realestate.springbootrealestate.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.PropertyResourceBundle;

/**
 * Statistics service
 */
@Service
@AllArgsConstructor
public class StatisticsService {

    private final ClickRepository clickRepository;
    private final UserRepository userService;

    public int calculateOptimalHourToPost(){
        List<Click> clicks = clickRepository.findAll();
        int sum = 0;
        for(Click click:clicks) {
            Date date = new Date(click.getDateCreated().getTime());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            sum += calendar.get(Calendar.HOUR_OF_DAY);
        }
        //To avoid arithmetic exceptions
        if(sum != 0) {
            int average = sum / clicks.size();
            return average;
        } else {
            return 0;
        }
    }

    public int getUserReachThisMonth(User user){
        Date currentDate = new Date(System.currentTimeMillis());
        List<Click> clicks = clickRepository.findByUser(user);
        int reach = 0;
        for(Click click:clicks) {
            Date date = new Date(click.getDateCreated().getTime());
            if(isSameDay(currentDate, date)) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                reach += 1;
            }
        }
        return reach;
    }

    public static boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMM");
        return fmt.format(date1).equals(fmt.format(date2));
    }

}
