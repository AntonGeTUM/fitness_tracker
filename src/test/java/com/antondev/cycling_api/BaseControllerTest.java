package com.antondev.cycling_api;

import com.antondev.cycling_api.auth.AuthService;
import com.antondev.cycling_api.auth.JwtService;
import com.antondev.cycling_api.ride.RideService;
import com.antondev.cycling_api.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public abstract class BaseControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockitoBean
    protected UserDetailsService userDetailsService;

    @MockitoBean
    protected AuthService authService;

    @MockitoBean
    protected UserService userService;

    @MockitoBean
    protected RideService rideService;

    @MockitoBean
    protected JwtService jwtService;

}
