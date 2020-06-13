package com.mouday;

public class Response {
    private int code;      // 状态码
    private String text;   // 响应文本
    private String method; // 请求方法
    private String url;    // 请求url

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                '}';
    }
}
