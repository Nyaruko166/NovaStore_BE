/*
 *  © 2023 Nyaruko166
 */

/*
 *  © 2023 Nyaruko166
 */

package com.sd64.novastore.config;

import java.util.HashMap;
import java.util.Map;

public class GHNConfig {

    public static String apiProvinces = "https://online-gateway.ghn.vn/shiip/public-api/master-data/province";

    public static String apiDistricts = "https://online-gateway.ghn.vn/shiip/public-api/master-data/district?province_id=";

    public static String apiWard = "https://online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id=";

    public static String apiServices = "https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/available-services";

    public static String apiFee = "https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee";

    public static String headerToken = "ae5e892b-8b9b-11ee-96dc-de6f804954c9";

    public static Map<String, Integer> senderId = new HashMap<>();

    static {
        senderId.put("shopID", 1187844);
        senderId.put("provinceID", 201);
        senderId.put("districtID", 3440);
        senderId.put("wardCode", 13010);
    }

}
