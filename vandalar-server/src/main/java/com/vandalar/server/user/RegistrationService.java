package com.vandalar.server.user;

import com.vandalar.server.user.persistense.UserEntity;
import com.vandalar.server.user.persistense.UserRepository;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RegistrationService {

    private final UserRepository repository;

    public RegistrationService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public String register(String privateId, String name) {
        if (repository.existsByPrivateId(privateId)) {
            throw new IllegalArgumentException("private_id \"" + privateId + "\" is taken already");
        }

        String publicId = generatePublicId();

        repository.save(new UserEntity(privateId, name, publicId));

        return publicId;
    }

    private String generatePublicId() {
        for (; ; ) {
            String publicId = UUID.randomUUID().toString();
            if (!repository.existsByPublicId(publicId)) {
                return publicId;
            }
        }
    }
}