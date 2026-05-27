package com.antondev.cycling_api.user;

import com.antondev.cycling_api.user.dto.UpdateUserRequest;
import com.antondev.cycling_api.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor //constructor for final and non-null fields
public class UserService {

    private final UserRepository userRepository;

    private UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .weightKg(user.getWeightKg())
                .ftpWatts(user.getFtpWatts())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public UserResponse updateMe(User currentUser, UpdateUserRequest request) {
        if (request.getWeightKg() != null) {
            currentUser.setWeightKg(request.getWeightKg());
        }
        if (request.getFtpWatts() != null) {
            currentUser.setFtpWatts(request.getFtpWatts());
        }
        userRepository.save(currentUser);
        return toResponse(currentUser);
    }

    public UserResponse getMe(User currentUser) {
        return toResponse(currentUser);
    }
}
