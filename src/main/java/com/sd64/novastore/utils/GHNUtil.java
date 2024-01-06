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
import com.sd64.novastore.model.District;
import com.sd64.novastore.model.Province;
import com.sd64.novastore.model.Ward;
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
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GHNUtil {

    public List<Province> lstProvince = new ArrayList<>();

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

    public List<Province> getProvinces() {

        JsonArray resultArray = sendRequest(GHNConfig.apiProvinces);

        lstProvince = gson.fromJson(resultArray, new TypeToken<List<Province>>() {
        }.getType());

        return lstProvince;
    }

    public List<District> getDistricts(String provinceID) {

        JsonArray resultArray = sendRequest(GHNConfig.apiDistricts + provinceID);
        return gson.fromJson(resultArray, new TypeToken<List<District>>() {
        }.getType());
    }

    public List<Ward> getWard(String districtID) {

        JsonArray resultArray = sendRequest(GHNConfig.apiWard + districtID);
        return gson.fromJson(resultArray, new TypeToken<List<Ward>>() {
        }.getType());
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

    public static void main(String[] args) {
        GHNUtil ghnUtil = new GHNUtil();
//        List<Province> lstProvince = ghnUtil.getProvinces();
//        List<District> lstDistrict = ghnUtil.getDistricts(String.valueOf(201));
//        List<Ward> lstWard = ghnUtil.getWard(String.valueOf(3440));
        //WardCode 13010
//        for (Province x : lstProvince) {
//            System.out.println(x.toString());
//        }
//        for (District x : lstDistrict) {
//            System.out.println(x.toString());
//        }
//        for (Ward x : lstWard) {
//            System.out.println(x.toString());
//        }
        ghnUtil.getServices();
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("shop_id", GHNConfig.senderId.get("shopID"));
//        jsonObject.addProperty("from_district", GHNConfig.senderId.get("districtID"));
//        jsonObject.addProperty("to_district", 1442);
//        System.out.println(jsonObject);
    }

}
