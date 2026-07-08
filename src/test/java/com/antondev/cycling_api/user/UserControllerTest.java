package com.antondev.cycling_api.user;

import com.antondev.cycling_api.BaseControllerTest;
import com.antondev.cycling_api.user.dto.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

@WebMvcTest(UserController.class)
public class UserControllerTest extends BaseControllerTest {

    @Test
    @WithMockUser
    void getMe_authenticated_returnsOk() throws Exception {
        when(userService.getMe(any())).thenReturn(new UserResponse());

        mockMvc.perform(get("/users/me")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void updateMe_authenticated_returnsOk() throws Exception {
        when(userService.updateMe(any(), any())).thenReturn(new UserResponse());

        mockMvc.perform(patch("/users/me")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "weightKg": 60.0,
                        "ftpWatts": 200
                    }
                """))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
