package com.mouday;

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * http 请求类，接口参数参考了Python 的Requests库
 */
public class Requests {

    /**
     *
     * @param method GET POST ...
     * @param url
     * @param params
     * @param body
     * @param headers
     * @return
     * @throws IOException
     */
    public static Response request(
            String method,
            String url,
            Map<String, String> params,
            String body,
            Map<String, String> headers
    ) throws IOException {

        // 处理请求参数
        if ("GET".equals(method)) {
            url = getRequestUrl(url, params);
        }

        // 包装响应对象
        Response response = new Response();
        response.setUrl(url);
        response.setMethod(method);

        //得到connection对象
        URL httpUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();

        //设置请求方式
        connection.setRequestMethod(method);
        connection.setDoOutput(true);

        // 设置请求头
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        // 设置请求体
        if ("POST".equals(method)) {
            setRequestBody(connection, body);
        }

        //连接
        connection.connect();

        // 获取状态码
        response.setCode(connection.getResponseCode());

        // 获取响应结果
        if (response.getCode() == HttpURLConnection.HTTP_OK) {
            response.setText(getResponseText(connection));
        }

        // 断开连接
        connection.disconnect();

        return response;
    }

    public static Response request(
            String method,
            String url
    ) throws IOException {
        return request(method, url, null, null, null);
    }


    public static Response request(
            String method,
            String url,
            Map<String, String> params,
            String body
    ) throws IOException {
        return request(method, url, params, body, null);
    }


    private static String getRequestUrl(String url, Map<String, String> data) {
        if (data == null) {
            return url;
        } else {
            return url + "?" + getEncodeUrl(data);
        }
    }

    public static String getEncodeUrl(Map<String, String> data) {
        List<String> list = new ArrayList<>();

        for (Map.Entry<String, String> entry : data.entrySet()) {
            list.add(entry.getKey() + "=" + entry.getValue());
        }

        return String.join("&", list);
    }

    public static String getResponseText(HttpURLConnection connection) throws IOException {
        InputStream inputStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line = null;

        StringBuffer buffer = new StringBuffer();

        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        reader.close();

        return buffer.toString();
    }

    public static void setRequestBody(HttpURLConnection connection, String body) throws IOException {
        if (body == null) return;

        OutputStream outputStream = connection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        writer.write(body);
        writer.close();
    }

    public static Response get(String url) throws IOException {
        return request("GET", url);
    }

    public static Response get(String url, Map<String, String> params) throws IOException {
        return request("GET", url, params, null);
    }

    public static Response post(String url) throws IOException {
        return request("POST", url);
    }

    public static Response post(String url, Map<String, String> params, String body) throws IOException {
        return request("POST", url, params, body);
    }

    public static Response postData(String url, Map<String, String> params, Map<String, String> data) throws IOException {
        return request("POST", url, params, getEncodeUrl(data), null);
    }

    /**
     * 设置参数类型是json格式
     * @param url
     * @param params
     * @param data
     * @return
     * @throws IOException
     */
    public static Response postJson(String url, Map<String, String> params, Map<String, String> data) throws IOException {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=utf-8");
        String body = JSON.toJSONString(data);
        return request("POST", url, params, body, headers);
    }


}
