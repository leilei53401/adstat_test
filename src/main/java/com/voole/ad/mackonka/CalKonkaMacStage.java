package com.voole.ad.mackonka;

import com.voole.ad.utils.AdFileTools;
import com.voole.ad.utils.AgentUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-10-31.
 */
public class CalKonkaMacStage {
    
    public static void main(String[] args){

        //88795B000000 - 88795BFFFFFF
        calStage1();

        //001A341A0000 - 001A3455FFFF
//        calStage2();

    }


    public static void calStage1(){

//        long start = 0x88795B000000;
        String  prefix = "88795B";
//        String  prefix = "001A34";
        List<String> list = new ArrayList<String>();
        //out
        int out = 0x00;
        while(out<=0xff){
            String outStr  = IntToHex(out);
            //midd
            int midd = 0x00;

            while(midd<=0xff){

                String midStr  = IntToHex(midd);

                //innner
                int num  = 0x00;
                while(num<=0xff){

//                    System.out.println(num+" , "+IntToHex(num));
                    String intStr  = IntToHex(num);

//                    System.out.println(prefix + outStr + midStr +intStr);

                    String outMac  = prefix + outStr + midStr +intStr;
//                    String outMac  = prefix  + midStr +intStr;

                    String  outMD5 = AgentUtils.toMD5(outMac.toUpperCase());

                    String outLine = outMac + "," + outMD5;
                    list.add(outLine);

//               AdFileTools.writeLineToFile("E:\\opt\\data\\konkamacnew\\88795B\\88795B.txt",outLine,true);

                    num = num+0x1;
                }

                //内循环结束写一次
                AdFileTools.writeBatchToFile("E:\\opt\\data\\konkamacnew\\88795B\\","88795B.txt",list,true);
                list.clear();

                midd = midd+0x1;
            }

            out = out +0x1;

            System.out.println("out ["+outStr+"] done!");

        }

        System.out.println("done!");
    }

    public static void calStage2(){

//        long start = 0x88795B000000;
//        String  prefix = "88795B";
        String  prefix = "001A34";
        List<String> list = new ArrayList<String>();
        //out
        int out = 0x1A;
        while(out<=0x55){
            String outStr  = IntToHex(out);
            //midd
            int midd = 0x00;

            while(midd<=0xff){

                String midStr  = IntToHex(midd);

                //innner
                int num  = 0x00;
                while(num<=0xff){

//                    System.out.println(num+" , "+IntToHex(num));
                    String intStr  = IntToHex(num);

//                    System.out.println(prefix + outStr + midStr +intStr);

                    String outMac  = prefix + outStr + midStr +intStr;
//                    String outMac  = prefix  + midStr +intStr;

                    String  outMD5 = AgentUtils.toMD5(outMac.toUpperCase());

                    String outLine = outMac + "," + outMD5;
                    list.add(outLine);

//               AdFileTools.writeLineToFile("E:\\opt\\data\\konkamacnew\\88795B\\88795B.txt",outLine,true);

                    num = num+0x1;
                }

                //内循环结束写一次
                AdFileTools.writeBatchToFile("E:\\opt\\data\\konkamacnew\\001A34\\","001A34.txt",list,true);
                list.clear();

                midd = midd+0x1;
            }

            out = out +0x1;

            System.out.println("out ["+outStr+"] done!");

        }

        System.out.println("done!");
    }


    public static void main1(String[] args){
        int num  = 0x00;
        while(num<=0xff) {
//                    System.out.println(num+" , "+IntToHex(num));
            String intStr = IntToHex(num);
            System.out.println(intStr);
            num = num + 0x1;
        }
    }



    //10进制转16进制
    public static String IntToHex(int n){
        char[] ch = new char[20];
        int nIndex = 0;
        while ( true ){
            int m = n/16;
            int k = n%16;
            if ( k == 15 )
                ch[nIndex] = 'F';
            else if ( k == 14 )
                ch[nIndex] = 'E';
            else if ( k == 13 )
                ch[nIndex] = 'D';
            else if ( k == 12 )
                ch[nIndex] = 'C';
            else if ( k == 11 )
                ch[nIndex] = 'B';
            else if ( k == 10 )
                ch[nIndex] = 'A';
            else
                ch[nIndex] = (char)('0' + k);
            nIndex++;
            if ( m == 0 )
                break;
            n = m;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(ch, 0, nIndex);
        sb.reverse();
//        String strHex = new String("0x");
        String  strHex = sb.toString();
        if(strHex.length()<2){
            strHex = "0"+strHex;
        }
        return strHex;
    }

    //16进制转10进制
    public static int HexToInt(String strHex){
        int nResult = 0;
        if ( !IsHex(strHex) )
            return nResult;
        String str = strHex.toUpperCase();
        if ( str.length() > 2 ){
            if ( str.charAt(0) == '0' && str.charAt(1) == 'X' ){
                str = str.substring(2);
            }
        }
        int nLen = str.length();
        for ( int i=0; i<nLen; ++i ){
            char ch = str.charAt(nLen-i-1);
            try {
                nResult += (GetHex(ch)*GetPower(16, i));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return nResult;
    }

    //计算16进制对应的数值
    public static int GetHex(char ch) throws Exception{
        if ( ch>='0' && ch<='9' )
            return (int)(ch-'0');
        if ( ch>='a' && ch<='f' )
            return (int)(ch-'a'+10);
        if ( ch>='A' && ch<='F' )
            return (int)(ch-'A'+10);
        throw new Exception("error param");
    }

    //计算幂
    public static int GetPower(int nValue, int nCount) throws Exception{
        if ( nCount <0 )
            throw new Exception("nCount can't small than 1!");
        if ( nCount == 0 )
            return 1;
        int nSum = 1;
        for ( int i=0; i<nCount; ++i ){
            nSum = nSum*nValue;
        }
        return nSum;
    }
    //判断是否是16进制数
    public static boolean IsHex(String strHex){
        int i = 0;
        if ( strHex.length() > 2 ){
            if ( strHex.charAt(0) == '0' && (strHex.charAt(1) == 'X' || strHex.charAt(1) == 'x') ){
                i = 2;
            }
        }
        for ( ; i<strHex.length(); ++i ){
            char ch = strHex.charAt(i);
            if ( (ch>='0' && ch<='9') || (ch>='A' && ch<='F') || (ch>='a' && ch<='f') )
                continue;
            return false;
        }
        return true;
    }

}
