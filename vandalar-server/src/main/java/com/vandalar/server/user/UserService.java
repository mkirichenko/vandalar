package com.vandalar.server.user;

import com.vandalar.server.user.persistense.UserEntity;
import com.vandalar.server.user.persistense.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User getUser(String privateId) {
        UserEntity userEntity = repository.getOne(privateId);

        if (userEntity == null) {
            throw new RuntimeException("user with id " + privateId + " is not found");
        }

        return toUser(userEntity);
    }

    private User toUser(UserEntity userEntity) {
        return new User(userEntity.getPrivateId(), userEntity.getPublicId(), userEntity.getName());
    }
}