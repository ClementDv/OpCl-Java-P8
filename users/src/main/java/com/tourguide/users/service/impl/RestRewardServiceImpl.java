package com.tourguide.users.service.impl;

import com.tourguide.users.properties.RewardServiceProperties;
import com.tourguide.users.service.RestRewardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Service
@Slf4j
public class RestRewardServiceImpl implements RestRewardService {

    private final RewardServiceProperties rewardServiceProperties;

    public RestRewardServiceImpl(RewardServiceProperties rewardServiceProperties) {
        this.rewardServiceProperties = rewardServiceProperties;
    }

    @Override
    public int getAttractionRewardPoint(UUID attractionId, UUID userId) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(rewardServiceProperties.getUrl())
                .path("/getAttractionRewardPoints")
                .queryParam("attractionId", attractionId)
                .queryParam("userId", userId);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Integer> response = restTemplate.getForEntity(
                builder.toUriString(),
                int.class
        );

        Integer rewardPoint = response.getBody();
        if (rewardPoint == null || rewardPoint == -1) {
            log.debug("Reward service warning : wrong response to get attraction Reward point");
            return -1;
        }
        log.debug("Reward service successfully called : Get attraction Reward point for attractionId {} and userId {}",
                attractionId, userId);
        return rewardPoint;
    }
}
