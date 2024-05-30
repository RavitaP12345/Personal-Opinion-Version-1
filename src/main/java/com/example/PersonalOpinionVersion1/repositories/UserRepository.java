package com.example.PersonalOpinionVersion1.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.PersonalOpinionVersion1.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

	UserEntity findByEmail(String email);

	UserEntity findByUserId(Long userId);


}
