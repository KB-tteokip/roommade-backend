package com.roommade.domain.user.controller;

import com.roommade.domain.user.code.UserSuccessCode;
import com.roommade.domain.user.dto.request.UserSignupRequest;
import com.roommade.domain.user.dto.response.UserSignupResponse;
import com.roommade.domain.user.service.UserService;
import com.roommade.global.response.ApiResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserSignupResponse>> signup(
            @Valid @RequestBody UserSignupRequest request) {
        UserSignupResponse response = userService.signup(request);
        return ResponseEntity.status(UserSuccessCode.USER_SIGNED_UP.getStatus())
                .body(ApiResponse.success(UserSuccessCode.USER_SIGNED_UP, response));
    }
}
