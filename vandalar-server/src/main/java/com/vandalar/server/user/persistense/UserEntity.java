package com.vandalar.server.user.persistense;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "users")
@Entity
public class UserEntity {

    @Id
    private String privateId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String publicId;

    public UserEntity() {
    }

    public UserEntity(String privateId, String name, String publicId) {
        this.privateId = privateId;
        this.name = name;
        this.publicId = publicId;
    }

    public String getPrivateId() {
        return privateId;
    }

    public void setPrivateId(String privateId) {
        this.privateId = privateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }
}