package com.voole.test;

import org.apache.commons.lang.StringUtils;

import java.util.HashSet;

/**
 * Created by Administrator on 2017-5-16.
 */
public class MyTest {


    public static void main(String[] args){
       /* String s  = "1494902046000";
        s = s.substring(0,10);
        System.out.print(s);*/
        HashSet<String> set = new HashSet<>();
        set.add("a");
        set.add("a");
        set.add("b");
        set.add("c");
        set.add("c");
        System.out.println(set.toString());

        String str = StringUtils.join(set,",");

        System.out.println(str);


    }
}
