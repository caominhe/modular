package com.fcar.be.core.security;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fcar.be.modules.identity.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        String targetUrl;

        // Nếu Email đã tồn tại -> Đã Onboard xong -> Cấp Token login ngay
        if (userRepository.existsByEmail(email)) {
            String token = tokenProvider.generateToken(email);
            targetUrl = "http://localhost:3000/oauth2/redirect?token=" + token;
        }
        // Nếu chưa tồn tại -> Lần đầu đăng nhập GG -> Điều hướng về trang đặt mật khẩu
        else {
            String encodedName = URLEncoder.encode(name != null ? name : "", StandardCharsets.UTF_8);
            String encodedEmail = URLEncoder.encode(email != null ? email : "", StandardCharsets.UTF_8);
            // Frontend sẽ bắt URL này để hiển thị form nhập Password + Phone
            targetUrl = "http://localhost:3000/oauth2/onboard?email=" + encodedEmail + "&name=" + encodedName;
        }

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}