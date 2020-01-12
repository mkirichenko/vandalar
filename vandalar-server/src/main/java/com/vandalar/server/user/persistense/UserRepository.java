package com.vandalar.server.user.persistense;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    boolean existsByPrivateId(String privateId);

    boolean existsByPublicId(String publicId);
	
	UserEntity findByPublicId(String publicId);
}