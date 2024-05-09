package org.omoknoone.ppm.domain.employee.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.domain.employee.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/refreshTokens")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/logout")
    public ResponseEntity<ResponseMessage> logout(@RequestHeader int refreshTokenId) throws AuthenticationException {

        authService.logout(refreshTokenId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
