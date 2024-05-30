package com.example.PersonalOpinionVersion1.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.PersonalOpinionVersion1.entities.ContactEntity;
@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, Long>{

	ContactEntity findByContactId(Long contactId);

	List<ContactEntity> findAllByContactId(Long valueOf);

}
