package com.demo.account.utils;

public class IndexUtil {
    public static String getString(String res, String key){
        Integer index = res.lastIndexOf(key);
        String firstStr = res.substring(0, index);
        Integer kuohaoIndex = firstStr.lastIndexOf("{");
        String secondStr = firstStr.substring(kuohaoIndex);
        Integer maohaoIndex, douhaoIndex;
        maohaoIndex = secondStr.indexOf(":");
        douhaoIndex = secondStr.indexOf(",");
        return secondStr.substring(maohaoIndex,douhaoIndex);
    }

    public static String getStringSecond(String res, String key){
        Integer index = res.lastIndexOf(key);
        String firstStr = res.substring(0, index);
        Integer kuohaoIndex = firstStr.lastIndexOf("{");
        String secondStr = firstStr.substring(kuohaoIndex);

        String thirdStr = secondStr.substring(secondStr.indexOf(",") + 1);
        Integer maohaoIndex, douhaoIndex;
        maohaoIndex = thirdStr.indexOf(":");
        douhaoIndex = thirdStr.indexOf(",");
        return thirdStr.substring(maohaoIndex, douhaoIndex);
    }
}
