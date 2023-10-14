package com.sd64.novastore.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sd64.novastore.config.MomoPaymentConfig;
import com.sd64.novastore.config.VNPaymentConfig;
import com.sd64.novastore.config.ZaloPayConfig;
import com.sd64.novastore.request.MomoPaymentRequest;
import com.sd64.novastore.service.PaymentService;
import com.sd64.novastore.util.payment.HMACUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {

    Gson gson = new Gson();

    @Override
    public String MomoPayCreate() throws IOException, URISyntaxException {

        int random_id = new Random().nextInt(1000000);

        MomoPaymentRequest momoPaymentRequest = MomoPaymentRequest.builder()
                .partnerCode(MomoPaymentConfig.partnerCode)
                .redirectUrl(MomoPaymentConfig.returnUrl)
                .ipnUrl(MomoPaymentConfig.ipnUrl)
                .amount(1000000L)
                .requestType("captureWallet")
                .requestId(String.valueOf(random_id))
                .orderId(String.valueOf(random_id))
                .orderInfo("NovaStore - Thanh Toan Don Hang #" + random_id)
                .lang("vi")
                .extraData("")
                .build();

        String signature = momoPaymentRequest.signatureGen(MomoPaymentConfig.accessKey, MomoPaymentConfig.secretKey);
        momoPaymentRequest.setSignature(signature);

        String json = gson.toJson(momoPaymentRequest);
//        System.out.println(json);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(MomoPaymentConfig.paymentUrl);
        StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        post.setEntity(requestEntity);

        // Content-Type: application/x-www-form-urlencoded
//        post.setEntity(new UrlEncodedFormEntity(params));


        CloseableHttpResponse res = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
        StringBuilder resultJsonStr = new StringBuilder();
        String line;

        while ((line = rd.readLine()) != null) {
            resultJsonStr.append(line);
        }

        return resultJsonStr.toString();
    }

    @Override
    public String zalopayCreate() throws IOException {

        int random_id = new Random().nextInt(1000000);

        JsonObject embed_data = new JsonObject();
        embed_data.addProperty("redirecturl", "http://localhost:8080/api/payment/zalo/return");
//        JsonObject item = new JsonObject();
//        item.addProperty("itemid", "knb");
//        item.addProperty("itemname", "kim nguyen bao");
//        item.addProperty("itemprice", "198400");
//        item.addProperty("itemquantity", 1);
//        jsonObject.add("item", item);

        Map<String, Object> order = new HashMap<String, Object>() {{
            put("app_id", ZaloPayConfig.appid);
            put("app_trans_id", ZaloPayConfig.getCurrentTimeString("yyMMdd") + "_" + random_id); // translation missing: vi.docs.shared.sample_code.comments.app_trans_id
            put("app_time", System.currentTimeMillis()); // milliseconds
            put("app_user", "Nyaruko166");
            put("amount", 10000);
            put("description", "NovaStore - Thanh Toan Don Hang #" + random_id);
            put("bank_code", "");
            put("item", "[{}]");
            put("embed_data", embed_data);
        }};

        String data = order.get("app_id") + "|" + order.get("app_trans_id") + "|" + order.get("app_user") + "|" + order.get("amount") + "|" + order.get("app_time") + "|" + order.get("embed_data") + "|" + order.get("item");
        order.put("mac", HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, ZaloPayConfig.key1, data));

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(ZaloPayConfig.endpointCreateOrder);

        List<NameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, Object> e : order.entrySet()) {
            params.add(new BasicNameValuePair(e.getKey(), e.getValue().toString()));
        }

        // Content-Type: application/x-www-form-urlencoded
        post.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse res = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
        StringBuilder resultJsonStr = new StringBuilder();
        String line;

        while ((line = rd.readLine()) != null) {
            resultJsonStr.append(line);
        }

        return resultJsonStr.toString();
    }

    @Override
    public String vnpayCreate(HttpServletRequest req, Long price) throws UnsupportedEncodingException {
        String vnp_Version = VNPaymentConfig.vnp_Version;
        String vnp_Command = VNPaymentConfig.vnp_Command;
        String orderType = VNPaymentConfig.orderType;
        long amount = price * 100;
        String bankCode = VNPaymentConfig.bankCode;

        String vnp_TxnRef = VNPaymentConfig.getTransactionNumber(8);
        String vnp_IpAddr = VNPaymentConfig.getIpAddress(req);

        String vnp_TmnCode = VNPaymentConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = req.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", VNPaymentConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPaymentConfig.hmacSHA512(VNPaymentConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        return VNPaymentConfig.vnp_PayUrl + "?" + queryUrl;
    }
}
