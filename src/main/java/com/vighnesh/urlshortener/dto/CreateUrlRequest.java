package com.vighnesh.urlshortener.dto;

public class CreateUrlRequest {
    private String originalUrl;

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }
}
