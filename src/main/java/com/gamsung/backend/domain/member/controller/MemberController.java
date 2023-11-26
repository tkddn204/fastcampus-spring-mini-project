package com.gamsung.backend.domain.member.controller;

import com.gamsung.backend.domain.member.controller.request.MemberControllerLoginRequest;
import com.gamsung.backend.domain.member.controller.request.MemberControllerRegisterEmailCheckRequest;
import com.gamsung.backend.domain.member.controller.request.MemberControllerRegisterRequest;
import com.gamsung.backend.domain.member.dto.request.MemberLoginRequest;
import com.gamsung.backend.domain.member.dto.request.MemberRegisterRequest;
import com.gamsung.backend.domain.member.dto.response.MemberLoginResponse;
import com.gamsung.backend.domain.member.dto.response.MemberLogoutResponse;
import com.gamsung.backend.domain.member.dto.response.MemberRegisterEmailCheckResponse;
import com.gamsung.backend.domain.member.dto.response.MemberRegisterResponse;
import com.gamsung.backend.domain.member.service.MemberService;
import com.gamsung.backend.global.common.ControllerResponse;
import com.gamsung.backend.global.resolver.AuthContext;
import com.gamsung.backend.global.resolver.MemberAuth;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.gamsung.backend.global.config.SwaggerDescriptionConfig.*;

@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    @Operation(summary = "로그인 API", description = MEMBER_LOGIN)
    public ResponseEntity<ControllerResponse> memberLogin(
            @Valid @RequestBody MemberControllerLoginRequest loginRequest
    ) {
        MemberLoginResponse response = memberService.login(MemberLoginRequest.from(loginRequest));
        return ResponseEntity.ok(ControllerResponse.builder()
                .code(1000)
                .data(response)
                .build());
    }

    @PostMapping("/register")
    @Operation(summary = "회원가입 API", description = MEMBER_RIGISTER)
    public ResponseEntity<ControllerResponse> memberRegister(
            @Valid @RequestBody MemberControllerRegisterRequest registerRequest
    ) {
        MemberRegisterResponse response = memberService.register(MemberRegisterRequest.from(registerRequest));
        return ResponseEntity.created(URI.create("/")).body(ControllerResponse.builder()
                .code(1003)
                .data(response)
                .build());
    }

    @GetMapping("/register/check")
    @Operation(summary = "이메일 중복체크 API", description = MEMBER_REGISTER_EMAIL_CHECK)
    public ResponseEntity<ControllerResponse> memberRegisterEmailCheck(
            @Valid MemberControllerRegisterEmailCheckRequest emailCheckRequest
    ) {
        MemberRegisterEmailCheckResponse response = memberService.emailCheck(emailCheckRequest.email());
        return ResponseEntity.ok(ControllerResponse.builder()
                .code(1006)
                .data(response)
                .build());
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃 API", description = MEMBER_LOGOUT)
    public ResponseEntity<ControllerResponse> memberLogout(
            @MemberAuth AuthContext authContext
    ) {
        MemberLogoutResponse response = memberService.logout(authContext.email());
        return ResponseEntity.ok(ControllerResponse.builder()
                .code(1011)
                .data(response)
                .build());
    }
}
