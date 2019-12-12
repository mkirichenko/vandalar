package com.vandalar.server.user.web;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class RegistrationRequest {

    @Pattern(
        regexp = "[a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8}",
        message = "${validatedValue} id not an UUID"
    )
    @NotEmpty
    private String privateId;

    @Length(max = 255)
    @NotEmpty
    private String name;

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
}