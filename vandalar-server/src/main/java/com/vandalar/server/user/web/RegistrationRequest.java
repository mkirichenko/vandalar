package com.vandalar.server.user.web;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegistrationRequest {

    @Pattern(
        regexp = "[a-fA-F0-9]{8}(-[a-fA-F0-9]{4}){4}[a-fA-F0-9]{8}",
        message = "${validatedValue} is not an UUID"
    )
    @NotEmpty
    private String privateId;

    @Size(max = 1000)
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