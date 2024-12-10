package com.meatigo.campaign.secondaryDbEntity;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "SecondaryEntity")
public class SecondaryEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String whatsAppSheduledMessageType;
    private Boolean isSheduledCampaign;
    private Boolean sheduledCampaignStatus;
    private Date sheduledCampaignTime;


}