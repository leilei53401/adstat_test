package com.voole.ad.test;

import org.joda.time.DateTime;

/**
 * Created by Administrator on 2017-7-16.
 */
public class MyTest {

    public static void  main(String[] args){
        DateTime dt = new DateTime();
//        DateTime newDt = dt.plusDays(-430);//2016-05-12
        DateTime newDt = dt.plusDays(-45);//2016-05-12
        String newDate = newDt.toString("yyyy-MM-dd");

        System.out.println(newDate);
    }

}
