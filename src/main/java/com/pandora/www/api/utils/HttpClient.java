package com.pandora.www.api.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class HttpClient {
    public static String doGet(String httpUrl, Map<String, String> headMap) {
        HttpURLConnection connection = null;
        InputStream is = null;

        BufferedReader br = null;
        String result = null; // return result String

        try {
//            创建远程url链接对象
            URL url = new URL(httpUrl);

//            通过远程url连接对象打开一个链接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();

//            设置连接请求方式：get
            connection.setRequestMethod("GET");

            //设置链接主机服务器的超时时间：150000毫秒
            connection.setConnectTimeout(15000);

            //设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(60000);

            //version 权限校验版本
            connection.setRequestProperty("version", headMap.get("version"));

            //timestamp 调用时间戳 ms
            connection.setRequestProperty("timestamp", headMap.get("timestamp"));

            //appkey 应用key
            connection.setRequestProperty("appKey", headMap.get("appKey"));

            //signature 本次抵用签名 生成算法 md5(appSecret + timestamp)
            connection.setRequestProperty("signature", MD5MsgDigest.digest(headMap.get("appSecret") + headMap.get("timestamp")));


            if (!headMap.get("apiToken").isEmpty()) {
                // apiToken 权限校验版本
                connection.setRequestProperty("apiToken", headMap.get("apiToken"));
            }

            //发送请求
            connection.connect();

            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();

                //封装输入流is，并指定字符集
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));


                //存放数据
                StringBuffer stringBuffer = new StringBuffer();

                String tmp = null;

                while ((tmp = br.readLine()) != null) {
                    stringBuffer.append(tmp);
                    stringBuffer.append("\r\n");
                }
                result = stringBuffer.toString();

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            connection.disconnect();
        }

        return result;
    }

    public static String doPost(String httpUrl, String param, Map<String, String> headMap) {
        HttpURLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        String result = null; // return result String

        try {
//            创建远程url链接对象
            URL url = new URL(httpUrl);

//            通过远程url连接对象打开一个链接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();

//            设置连接请求方式：get
            connection.setRequestMethod("GET");

            //设置链接主机服务器的超时时间：150000毫秒
            connection.setConnectTimeout(15000);

            //设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(60000);

            //默认值为false 当向远程服务器传送数据/写数据，需要设置为true
            connection.setDoOutput(true);

            //默认值为true 当向远程服务器读数据时，需要设置为true，该参数可有可无
            connection.setDoInput(true);

            // 设置传入参数的格式：请求参数应该是 name1=value&name2=value2的形式。

            connection.setRequestProperty("Content-Type", "application/json");



            //version 权限校验版本
            connection.setRequestProperty("version", headMap.get("version"));

            //timestamp 调用时间戳 ms
            connection.setRequestProperty("timestamp", headMap.get("timestamp"));

            //appkey 应用key
            connection.setRequestProperty("appKey", headMap.get("appKey"));

            //signature 本次抵用签名 生成算法 md5(appSecret + timestamp)
            connection.setRequestProperty("signature", MD5MsgDigest.digest(headMap.get("appSecret") + headMap.get("timestamp")));


            if (!headMap.get("apiToken").isEmpty()) {
                // apiToken 权限校验版本
                connection.setRequestProperty("apiToken", headMap.get("apiToken"));
            }


            // 通过连接对象获取一个输出流
            os = connection.getOutputStream();

            // 通过输出流对象将参数写出去/传输出去，他是通过字节数组写出的
            os.write(param.getBytes());

            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();

                //封装输入流is，并指定字符集
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));


                //存放数据
                StringBuffer stringBuffer = new StringBuffer();

                String tmp = null;

                //循环遍历一行一行读取数据
                while ((tmp = br.readLine()) != null) {
                    stringBuffer.append(tmp);
                    stringBuffer.append("\r\n");
                }
                result = stringBuffer.toString();

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            connection.disconnect();
        }

        return result;
    }
}
