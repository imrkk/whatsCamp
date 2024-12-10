package com.meatigo.campaign.secondaryDbRepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.meatigo.campaign.secondaryDbEntity.SecondaryEntity;

@Repository
public interface SecondaryRepository extends JpaRepository<SecondaryEntity, Long> {
	
	@Query(nativeQuery = true, value = "select * from SecondaryEntity se")
	public List<SecondaryEntity> getAllData();
	
	
	@Query(nativeQuery = true, value = "select * from SecondaryEntity se where se.isSheduledCampaign = true and se.sheduledCampaignStatus = false")
	public List<SecondaryEntity>  getAllSheduledData();
	
}