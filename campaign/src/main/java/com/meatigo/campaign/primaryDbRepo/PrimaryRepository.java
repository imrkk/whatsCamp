package com.meatigo.campaign.primaryDbRepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.meatigo.campaign.primaryDbEntity.PrimaryEntity;



@Repository
public interface PrimaryRepository extends JpaRepository<PrimaryEntity, Long> {
	
	@Query(nativeQuery = true, value = "select * from PrimaryEntity pe")
	public List<PrimaryEntity> getAllData();
}

