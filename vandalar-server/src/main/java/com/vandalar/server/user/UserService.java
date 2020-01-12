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
	
	    checkErrors(privateId, userEntity);
	
	    return toUser(userEntity);
    }
	
	public User getByPublicId(String publicId) {
		UserEntity userEntity = repository.findByPublicId(publicId);
		
		checkErrors(publicId, userEntity);
		
		return toUser(userEntity);
	}
	
	private void checkErrors(String publicId, UserEntity userEntity) {
		if (userEntity == null) {
			throw new RuntimeException("user with id " + publicId + " is not found");
		}
	}
	
	private User toUser(UserEntity userEntity) {
        return new User(userEntity.getPrivateId(), userEntity.getPublicId(), userEntity.getName());
    }
}