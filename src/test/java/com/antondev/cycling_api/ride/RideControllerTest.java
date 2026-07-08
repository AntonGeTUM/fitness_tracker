package com.antondev.cycling_api.ride;

import com.antondev.cycling_api.BaseControllerTest;
import com.antondev.cycling_api.ride.dto.RideResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest(RideController.class)
public class RideControllerTest extends BaseControllerTest {

    @Test
    @WithMockUser
    void getRides_authenticatedUser_returnsOk() throws Exception {
        when(rideService.getRides(any(), isNull(), isNull(), isNull(), isNull()))
                .thenReturn(List.of(new RideResponse()));

        mockMvc.perform(get("/rides")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getRides_unauthenticated_returns401() throws Exception {
        mockMvc.perform(get("/rides"))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }

    @Test
    @WithMockUser
    void getRide_authenticatedUser_returnsOk() throws Exception {
        when(rideService.getRide(any(), anyLong()))
                .thenReturn(new RideResponse());

        mockMvc.perform(get("/rides/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void createRide_authenticatedUser_returnsCreated() throws Exception {
        when(rideService.createRide(any(), any()))
                .thenReturn(new RideResponse());

        mockMvc.perform(post("/rides")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "title": "Morning ride",
                                "date": "2026-07-08",
                                "distanceKm": 25.5,
                                "durationSeconds": 3600,
                                "rideType": "ROAD"
                            }
                        """))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
