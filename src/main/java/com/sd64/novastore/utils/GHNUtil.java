/*
 *  © 2023 Nyaruko166
 */

/*
 *  © 2023 Nyaruko166
 */

package com.sd64.novastore.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sd64.novastore.config.GHNConfig;
import com.sd64.novastore.model.District;
import com.sd64.novastore.model.Province;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
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
}
