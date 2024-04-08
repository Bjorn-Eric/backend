package org.example.eric.controller.home.tests;

import org.example.eric.controller.home.HomeController;
import org.example.eric.model.User;
import org.example.eric.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class HomeControllerTest {

    @InjectMocks
    private HomeController homeController;

    @Mock
    private TaskService taskService;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    @Mock
    private User user;

    @Test
    public void testRenderHomePage() {
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(taskService.getAllByTodaysDate(user)).thenReturn(List.of());
        when(taskService.getUpcomingTasks(user)).thenReturn(List.of());
        when(taskService.getCompletedTasks(user)).thenReturn(List.of());

        String result = homeController.renderHomePage(model, user);

        assertEquals("home", result);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("tasks_today", List.of());
    }
}
