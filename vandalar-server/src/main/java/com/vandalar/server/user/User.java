package com.vandalar.server.user;

public class User {

    private String privateId;
    private String publicId;
    private String name;

    public User(String privateId, String publicId, String name) {
        this.privateId = privateId;
        this.publicId = publicId;
        this.name = name;
    }

    public String getPrivateId() {
        return privateId;
    }

    public String getPublicId() {
        return publicId;
    }

    public String getName() {
        return name;
    }
}