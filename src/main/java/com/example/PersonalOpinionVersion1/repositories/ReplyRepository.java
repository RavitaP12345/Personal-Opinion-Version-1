package com.example.PersonalOpinionVersion1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.PersonalOpinionVersion1.entities.ReplyEntity;
@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity, Long>{

}
