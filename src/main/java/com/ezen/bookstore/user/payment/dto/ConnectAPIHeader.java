package com.ezen.bookstore.user.payment.dto;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class ConnectAPIHeader {
    private HttpHeaders headers;
    private HttpEntity<String> entity;

    public ConnectAPIHeader(String v2Api) {
        this.headers = new HttpHeaders();
        this.entity = new HttpEntity<>(headers);
        headers.set("Authorization", "PortOne " + v2Api);
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    public String getHeaders() {
        return this.headers.toString();
    }
}
