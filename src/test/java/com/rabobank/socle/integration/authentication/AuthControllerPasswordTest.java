package com.rabobank.socle.integration.authentication;

import com.rabobank.socle.authentication.entity.LoginDTO;
import com.rabobank.socle.authentication.entity.RefreshTokenDTO;
import com.rabobank.socle.authentication.resetpassword.RequestPasswordDTO;
import com.rabobank.socle.authentication.resetpassword.ResetPasswordDTO;
import org.junit.Before;
import org.junit.Ignore;
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
public class AuthControllerPasswordTest {

    public static final String EXISTING_EMAIL = "admin@admin.admin";
    public static final String EXISTING_PASSWORD = "azerty123";
    public static final String NEW_PASSWORD = "1234";
    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        Locale.setDefault(Locale.US);
    }


    @Test
    public void refreshTokenTest() {
        RefreshTokenDTO login = login();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(AUTH_HEADER, TOKEN_PREFIX + login.getTokens().getAccessToken());

        ResetPasswordDTO resetPasswordDTO = new ResetPasswordDTO(NEW_PASSWORD);
        HttpEntity<ResetPasswordDTO> entity = new HttpEntity<>(resetPasswordDTO, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
            "/auth/reset-pass", entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void requestPassTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RequestPasswordDTO requestPasswordDTO = new RequestPasswordDTO(EXISTING_EMAIL);
        HttpEntity<RequestPasswordDTO> entity = new HttpEntity<>(requestPasswordDTO, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
            "/auth/request-pass", entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Ignore("It's not so clear how it should works")
    @Test
    public void restorePassTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RequestPasswordDTO requestPasswordDTO = new RequestPasswordDTO(EXISTING_EMAIL);
        HttpEntity<RequestPasswordDTO> entity = new HttpEntity<>(requestPasswordDTO, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
            "/auth/restore-pass", entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private RefreshTokenDTO login() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        LoginDTO request = new LoginDTO(EXISTING_EMAIL, EXISTING_PASSWORD);
        HttpEntity<LoginDTO> entity = new HttpEntity<>(request, headers);

        ResponseEntity<RefreshTokenDTO> response = restTemplate.postForEntity(
            "/auth/login", entity, RefreshTokenDTO.class);
        return response.getBody();
    }
}
