package com.tourguide.users.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourguide.users.data.TestData;
import com.tourguide.users.dto.LocationDto;
import com.tourguide.users.dto.NearbyAttractionDto;
import com.tourguide.users.dto.UserRewardDto;
import com.tourguide.users.dto.VisitedLocationDto;
import com.tourguide.users.exceptions.ErrorCodesEnum;
import com.tourguide.users.exceptions.InvalidUserNameException;
import com.tourguide.users.exceptions.UserNotFoundException;
import com.tourguide.users.service.TourGuideService;
import com.tourguide.users.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tripPricer.Provider;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.tourguide.users.exceptions.ErrorCodesEnum.INVALID_USERNAME;
import static com.tourguide.users.exceptions.ErrorCodesEnum.USER_NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TourGuideController.class)
@Slf4j
public class TourGuideControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper jsonMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private TourGuideService tourGuideService;

    @Test
    public void indexTest() throws Exception {
        MvcResult result = mvc.perform(get("/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertThat(result.getResponse().getContentAsString()).isEqualTo("Greetings from TourGuide!");
    }

    @Test
    public void getLocation() throws Exception {
        VisitedLocationDto visitedLocationDto = TestData.generateAVisitedLocationDto(UUID.randomUUID());
        Mockito.when(userService.getLastVisitedLocation("test")).thenReturn(visitedLocationDto);
        MvcResult result = mvc.perform(get("/getLocation")
                .param("userName", "test")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();


        VisitedLocationDto resultVisitedLocationDto = jsonMapper.readValue(result.getResponse().getContentAsString(), VisitedLocationDto.class);
        Assertions.assertThat(resultVisitedLocationDto).isEqualTo(visitedLocationDto);

        // User not found

        Mockito.when(userService.getLastVisitedLocation("test")).thenThrow(new UserNotFoundException("test"));
        mvc.perform(get("/getLocation")
                .param("userName", "test")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(USER_NOT_FOUND.getStatus()))
                .andReturn();
    }

    @Test
    public void getAllCurrentLocationsTest() throws Exception {
        Map<UUID, LocationDto> locationMap = TestData.getLocationMapFromUserList(
                TestData.generateAUserList(8));
        Mockito.when(userService.getAllCurrentLocation()).thenReturn(locationMap);
        MvcResult result = mvc.perform(get("/getAllCurrentLocations")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();


        Map<UUID, LocationDto> resultLocationMap = jsonMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        Assertions.assertThat(resultLocationMap).isEqualTo(locationMap);
    }

    @Test
    public void getRewardsTest() throws Exception {
        List<UserRewardDto> userRewardList = TestData.generateAUserRewardDtoList(UUID.randomUUID(), 3);
        Mockito.when(userService.getUserRewards("test")).thenReturn(userRewardList);
        MvcResult result = mvc.perform(get("/getRewards")
                .param("userName", "test")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();


        List<UserRewardDto> resultUserRewardList = jsonMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        Assertions.assertThat(resultUserRewardList).isEqualTo(userRewardList);

        // Invalid User

        Mockito.when(userService.getLastVisitedLocation("test")).thenThrow(new InvalidUserNameException("test"));
        mvc.perform(get("/getLocation")
                .param("userName", "test")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(INVALID_USERNAME.getStatus()));
    }

    @Test
    public void getTripDeals() throws Exception {
        List<Provider> providerList = TestData.getProviderList();
        Mockito.when(tourGuideService.getTripDeals("test")).thenReturn(providerList);
        MvcResult result = mvc.perform(get("/getTripDeals")
                .param("userName", "test")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void getNearbyAttractions() throws Exception {
        List<NearbyAttractionDto> nearbyAttractionList = TestData.getNearbyAttractionDtoList(
                TestData.generateALocationDto(), 200D, 5);
        Mockito.when(tourGuideService.getNearbyAttractions("test")).thenReturn(nearbyAttractionList);
        MvcResult result = mvc.perform(get("/getNearbyAttractions")
                .param("userName", "test")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<NearbyAttractionDto> resultUserRewardList = jsonMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        Assertions.assertThat(resultUserRewardList).isEqualTo(nearbyAttractionList);
    }

}
