package com.rabobank.socle.integration.authentication;

import com.rabobank.socle.authentication.entity.LoginDTO;
import com.rabobank.socle.authentication.entity.RefreshTokenDTO;
import com.rabobank.socle.authentication.entity.SignUpDTO;
import com.rabobank.socle.authentication.entity.Tokens;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthControllerOAuthTest {

    public static final String EXISTING_EMAIL = "admin@admin.admin";
    public static final String EXISTING_PASSWORD = "azerty123";
    public static final String NEW_EMAIL = "user123@user.com";
    public static final String NEW_PASSWORD = "1234";
    public static final String USERNAME = "user123";

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        Locale.setDefault(Locale.US);
    }

    @Test
    public void loginTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        LoginDTO request = new LoginDTO(EXISTING_EMAIL, EXISTING_PASSWORD);
        HttpEntity<LoginDTO> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
            "/auth/login", entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("access_token", "refresh_token", "expires_in");
    }

    @Test
    public void signUpTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        SignUpDTO request = SignUpDTO.builder()
            .email(NEW_EMAIL)
            .fullName(USERNAME)
            .age(29)
            .countryCode("fr")
            .password(NEW_PASSWORD)
            .confirmPassword(NEW_PASSWORD)
            .build();

        HttpEntity<SignUpDTO> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
            "/auth/sign-up", entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("access_token", "refresh_token", "expires_in");
    }

    @Test
    public void refreshTokenTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Tokens currentTokens = refreshTokenDTO().getTokens();
        RefreshTokenDTO requestToken = new RefreshTokenDTO(
            new Tokens(0L, currentTokens.getAccessToken(), currentTokens.getRefreshToken()));

        HttpEntity<RefreshTokenDTO> entity = new HttpEntity<>(refreshTokenDTO(), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
            "/auth/refresh-token", entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("access_token", "refresh_token", "expires_in");
    }

    private RefreshTokenDTO refreshTokenDTO() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        LoginDTO request = new LoginDTO(EXISTING_EMAIL, EXISTING_PASSWORD);
        HttpEntity<LoginDTO> entity = new HttpEntity<>(request, headers);

        ResponseEntity<RefreshTokenDTO> response = restTemplate.postForEntity(
            "/auth/login", entity, RefreshTokenDTO.class);
        return response.getBody();
    }
}
