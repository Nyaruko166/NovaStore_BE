package com.sd64.novastore.config;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class ZaloPayConfig {

    public static String appid = "2553";
    public static String key1 = "PcY4iZIKFCIdgZvA6ueMcMHHUbRLYjPL";
    public static String key2 = "kLtgPl8HHhfvMuDHPwKfgfsY4Ydm9eIz";
    public static String endpointCreateOrder = "https://sb-openapi.zalopay.vn/v2/create";
    public static String endpointGetBanks = "https://sbgateway.zalopay.vn/api/getlistmerchantbanks";

    public static String getCurrentTimeString(String format) {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        fmt.setCalendar(cal);
        return fmt.format(cal.getTimeInMillis());
    }


}
