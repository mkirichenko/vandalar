package com.vandalar.server.user;

import static com.vandalar.server.util.HttpTestUtil.toJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vandalar.server.user.web.RegistrationRequest;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class RegistrationResourceTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .build();
    }

    @Test
    void registerSuccessTest() throws Exception {
        register(privateId(), "name")
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.publicId").exists());
    }

    private String privateId() {
        return UUID.randomUUID().toString();
    }

    private ResultActions register(String privateId, String name) throws Exception {
        RegistrationRequest request = new RegistrationRequest();
        request.setPrivateId(privateId);
        request.setName(privateId);

        return mockMvc.perform(post("/api/v1/auth/register")
            .content(toJson(request))
            .contentType(MediaType.APPLICATION_JSON));
    }
}