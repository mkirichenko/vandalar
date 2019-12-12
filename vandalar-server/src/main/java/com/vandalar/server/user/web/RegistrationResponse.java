package com.vandalar.server.user.web;

public class RegistrationResponse {

    private String publicId;

    public RegistrationResponse() {
    }

    public RegistrationResponse(String publicId) {
        this.publicId = publicId;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }
}