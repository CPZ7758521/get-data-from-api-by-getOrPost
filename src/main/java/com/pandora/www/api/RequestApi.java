package com.pandora.www.api;

import com.pandora.www.api.utils.HttpClient;

import java.util.HashMap;

public class RequestApi {
    public static void main(String[] args) {
        try {
            HashMap<String, String> headerMap = new HashMap<>();

            headerMap.put("appKey", "9a81a4958bb4423a4cba9c42d0d91f");
            headerMap.put("appSecret", "15132419fa39498483e58b1b9d449943");
            headerMap.put("timestamp", String.valueOf(System.currentTimeMillis()));

            //GET方式
            headerMap.put("version", "v1");
            headerMap.put("apiToken", "309778e2d97e4c6f93d79d52a8feded2");

            String getUrl = "http://test-bigdata001.tkamc.com:10073/easy-data-api/dataworks/test/hbase_test?areaLevel=3&startDate=2021-12-31&endDate=2021-12-31";

            String getResult = HttpClient.doGet(getUrl, headerMap);

            System.out.println("GET" + ":\n" + getResult);


            //POST方式
            headerMap.put("version", "v1");
            headerMap.put("apiToken", "309778e2d97e4c6f93d79d52a8feded2");

            String postUrl = "http://test-bigdata001.tkamc.com:10073/easy-data-api/dataworks/test/hbase_test";

            String params = "{'by_date':'20200616'}";

//            String params = "{'rowkey_start':'1000000001','rowkey_end':'10000001'}";

            String postResult = HttpClient.doPost(postUrl, params, headerMap);

            System.out.println("POST" + ":\n" + postResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
