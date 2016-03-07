package com.k2udacity.moviestage2.utils;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String convertDateFormat(String dateStr,String srcFormat,String destFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(srcFormat);
        String formattedDate;
        try {
            Date dateRelease = sdf.parse(dateStr);
            formattedDate = DateFormat.format(destFormat, dateRelease).toString();
        } catch (ParseException e) {
            e.printStackTrace();
            formattedDate = srcFormat;
        }
        return formattedDate;
    }
}
