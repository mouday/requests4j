package com.mouday;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestRequests {
    public static void main(String[] args) throws IOException {
        String getUrl = "http://httpbin.org/get";
        String postUrl = "http://httpbin.org/post";

        Map<String, String> data = new HashMap<>();
        data.put("key", "value");

        Response response = null;

        response = Requests.get(getUrl, data);
        System.out.println(response.getText());

        response = Requests.postData(postUrl, null, data);
        System.out.println(response.getText());

        response = Requests.postJson(postUrl, null, data);
        System.out.println(response.getText());
    }
}
