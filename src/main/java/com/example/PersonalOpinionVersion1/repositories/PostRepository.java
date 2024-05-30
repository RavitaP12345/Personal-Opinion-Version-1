package com.example.PersonalOpinionVersion1.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.PersonalOpinionVersion1.entities.PostEntity;
import com.example.PersonalOpinionVersion1.entities.UserEntity;
@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long>{

	List<PostEntity> findByUser(UserEntity byUserId);

	PostEntity findByPostId(Long postId);

	PostEntity getByPostId(Long postId);

}
