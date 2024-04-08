package org.example.eric.controller.login.tests;

import org.example.eric.controller.login.LoginController;
import org.example.eric.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private Authentication authentication;

    @Mock
    private User user;


    @Test
    public void testLoginRedirectWhenAuthenticated() {
        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getActive()).thenReturn(true);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        String result = loginController.renderLoginPage(user);
        assertEquals("redirect:/home", result);
    }

    @Test
    public void testRenderLoginPageWhenNotAuthenticated() {
        String result = loginController.renderLoginPage(null);
        assertEquals("login", result);
    }
}
