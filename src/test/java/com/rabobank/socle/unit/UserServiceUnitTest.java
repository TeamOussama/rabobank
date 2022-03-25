package com.rabobank.socle.unit;

import com.rabobank.socle.authentication.entity.SignUpDTO;
import com.rabobank.socle.common.exception.BadRequestHttpException;
import com.rabobank.socle.data.entity.User;
import com.rabobank.socle.data.repository.UserRepository;
import com.rabobank.socle.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceUnitTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;


    private SignUpDTO signUpDTO;
    private SignUpDTO signUpDTOWithAgeLessThan18;
    private User expectedUser;


    @Before
    public void Before() {
        signUpDTO = SignUpDTO.builder()
            .email("Htitioussama@gmail.com")
            .fullName("Oussama htiti")
            .age(29)
            .countryCode("fr")
            .password("isASecret")
            .confirmPassword("isASecret")
            .build();

        signUpDTOWithAgeLessThan18 = SignUpDTO.builder()
            .email("Htitioussama@gmail.com")
            .fullName("Oussama htiti")
            .age(15)
            .countryCode("fr")
            .password("isASecret")
            .confirmPassword("isASecret")
            .build();

        expectedUser = User.builder()
            .email("Htitioussama@gmail.com")
            .age(29)
            .build();
    }


    @Test
    public void shouldRegister_Success() {
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        User actualUser = userService.register(signUpDTO);

        assertEquals(expectedUser.getAge(), actualUser.getAge());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
    }

    @Test(expected = BadRequestHttpException.class)
    public void shouldRegister_Failed() {
        userService.register(signUpDTOWithAgeLessThan18);
    }

}
