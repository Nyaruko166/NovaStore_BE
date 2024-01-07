/*
 *  © 2023 Nyaruko166
 */

/*
 *  © 2023 Nyaruko166
 */

package com.sd64.novastore.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.sd64.novastore.config.GHNConfig;
import com.sd64.novastore.config.MomoPaymentConfig;
import com.sd64.novastore.model.Address;
import com.sd64.novastore.model.District;
import com.sd64.novastore.model.Province;
import com.sd64.novastore.model.Ward;
import com.sd64.novastore.request.GHNRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GHNUtil {

    Gson gson = new Gson();

    private JsonArray sendRequest(String apiUrl) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet post = new HttpGet(apiUrl);
        post.addHeader("token", GHNConfig.headerToken);


        JsonObject resultObject = new JsonObject();
        try {
            CloseableHttpResponse res = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
            Gson gson = new Gson();
            resultObject = gson.fromJson(rd, JsonObject.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultObject.get("data").getAsJsonArray();
    }

    public Integer getProvinces(String Province) {
        JsonArray resultArray = sendRequest(GHNConfig.apiProvinces);
        List<Province> lstProvince = gson.fromJson(resultArray, new TypeToken<List<Province>>() {
        }.getType());

        for (Province x : lstProvince) {
            if (Province.contains(x.getProvinceName())) {
                return x.getProvinceID();
            }
        }
        return null;
    }

    public Integer getDistricts(Integer provinceID, String province) {
        JsonArray resultArray = sendRequest(GHNConfig.apiDistricts + provinceID);
        List<District> lstDistrict = gson.fromJson(resultArray, new TypeToken<List<District>>() {
        }.getType());

        for (District x : lstDistrict) {
            if (province.contains(x.getDistrictName())) {
                return x.getDistrictID();
            }
        }

        return null;
    }

    public String getWard(Integer districtID, String ward) {

        JsonArray resultArray = sendRequest(GHNConfig.apiWard + districtID);
        List<Ward> lstWard = gson.fromJson(resultArray, new TypeToken<List<Ward>>() {
        }.getType());

        for (Ward x : lstWard) {
            if (ward.contains(x.getWardName())) {
                return x.getWardCode();
            }
        }

        return null;
    }

    public void getServices() {

        JsonObject jsonPost = new JsonObject();
        jsonPost.addProperty("shop_id", GHNConfig.senderId.get("shopID"));
        jsonPost.addProperty("from_district", GHNConfig.senderId.get("districtID"));
        jsonPost.addProperty("to_district", 1442);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(GHNConfig.apiServices);
        post.addHeader("token", GHNConfig.headerToken);
        StringEntity requestEntity = new StringEntity(jsonPost.toString(), ContentType.APPLICATION_JSON);
        post.setEntity(requestEntity);

        JsonObject resultObject = new JsonObject();
        try {
            CloseableHttpResponse res = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
            Gson gson = new Gson();
            resultObject = gson.fromJson(rd, JsonObject.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String formattedResult = prettyPrintUsingGson(resultObject.get("data").getAsJsonArray().toString());
        System.out.println(formattedResult);
    }

    public String prettyPrintUsingGson(String uglyJson) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement jsonElement = JsonParser.parseString(uglyJson);
        return gson.toJson(jsonElement);
    }

    public BigDecimal calculateShippingFee(Address address, Integer quantity) {

        Integer provinceID = getProvinces(address.getCity());
        Integer districtID = getDistricts(provinceID, address.getDistrict());
        String wardID = getWard(districtID, address.getWard());

        GHNRequest ghnRequest = GHNRequest.builder()
                .service_type_id(2).weight(500 * quantity)
                .from_district_id(GHNConfig.senderId.get("districtID"))
                .from_ward_code(String.valueOf(GHNConfig.senderId.get("wardCode")))
                .to_district_id(districtID).to_ward_code(wardID)
                .build();

        String jsonPost = gson.toJson(ghnRequest);
//        System.out.println(jsonPost);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(GHNConfig.apiFee);
        post.addHeader("token", GHNConfig.headerToken);
        post.addHeader("shop_id", String.valueOf(GHNConfig.senderId.get("shopID")));
        StringEntity requestEntity = new StringEntity(jsonPost, ContentType.APPLICATION_JSON);
        post.setEntity(requestEntity);

        JsonObject resultObject = new JsonObject();
        try {
            CloseableHttpResponse res = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
            Gson gson = new Gson();
            resultObject = gson.fromJson(rd, JsonObject.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return BigDecimal.valueOf(resultObject.get("data").getAsJsonObject().get("total").getAsLong());
    }

    public static void main(String[] args) {
        GHNUtil ghnUtil = new GHNUtil();
//        Address address = Address.builder().city("Thành phố Hà Nội")
//                .district("Quận Nam Từ Liêm").ward("Phường Xuân Phương").build();
//        System.out.println(ghnUtil.getProvinces(address.getCity()));
//        System.out.println(ghnUtil.getDistricts(201, address.getDistrict()));
//        System.out.println(ghnUtil.getWard(3440, address.getWard()));
//        ghnUtil.getServices();

        Address toAddress = Address.builder()
                .city("Thành phố Hà Nội").district("Huyện Quốc Oai").ward("Xã Yên Sơn")
                .build();

//        System.out.println(ghnUtil.getProvinces(toAddress.getCity()));
//        System.out.println(ghnUtil.getDistricts(201, toAddress.getDistrict()));
//        System.out.println(ghnUtil.getWard(2004, toAddress.getWard()));


    }

}
