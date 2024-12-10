package com.meatigo.campaign.secondaryDbRepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import com.meatigo.campaign.secondaryDbEntity.WhatsAppCampaign;

import jakarta.transaction.Transactional;

@Repository
public interface WhatsAppCampaignRepo extends JpaRepository<WhatsAppCampaign, Long>{
	
	@Query(nativeQuery = true, value = "select * from WhatsAppCampaign wac where wac.campaignName = (:campaignName)")
	WhatsAppCampaign findCampaignByCampaignName(String campaignName);

	@Query(nativeQuery = true, value = "select * from WhatsAppCampaign wac where wac.isSheduledCampaign = true and wac.sheduledCampaignStatus = false and wac.isSheduleCampaignActive = true")
	List<WhatsAppCampaign> getAllSheduledData();
	
	@Query(nativeQuery = true, value = "select * from WhatsAppCampaign")
	List<WhatsAppCampaign> getAllCampaignData();
	
	@Query(nativeQuery = true, value = "select * from WhatsAppCampaign where isSheduledCampaign = (:isSheduledCampaign)")
	List<WhatsAppCampaign> findDataByIsSheduledCampaignFlag(boolean isSheduledCampaign);

	@Transactional
	@Modifying
	Integer deleteByCampaignName(String campaignName);

	WhatsAppCampaign findAllByCampaignName(String campaignName);

	Boolean existsByCampaignName(String campaignName);
	

}
