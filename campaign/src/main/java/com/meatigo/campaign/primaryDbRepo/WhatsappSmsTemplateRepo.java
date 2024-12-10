package com.meatigo.campaign.primaryDbRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meatigo.campaign.primaryDbEntity.WhatsappSmsTemplate;

@Repository
public interface WhatsappSmsTemplateRepo extends JpaRepository<WhatsappSmsTemplate, Long> {

	WhatsappSmsTemplate findByCode(String code);

	WhatsappSmsTemplate findByWhatsappSmsTemplateId(Long whatsappSmsTemplateId);

}