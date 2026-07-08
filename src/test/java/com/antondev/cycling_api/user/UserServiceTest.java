package com.antondev.cycling_api.user;

import com.antondev.cycling_api.user.dto.UpdateUserRequest;
import com.antondev.cycling_api.user.dto.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void updateMe_updatesFieldsCorrectly() {
        User user = new User();
        user.setWeightKg(70.0);

        UpdateUserRequest request = new UpdateUserRequest(60.0, 200);

        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        UserResponse response = userService.updateMe(user, request);

        assertThat(response.getWeightKg()).isEqualTo(user.getWeightKg());
    }

}
