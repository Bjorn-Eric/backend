package org.example.eric.controller.error.tests;

import org.example.eric.controller.error.ErrorController;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.NoHandlerFoundException;

import static junit.framework.TestCase.assertEquals;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ErrorControllerTest {

    @Autowired
    private ErrorController errorController;

    @Mock
    private Model model;

    @Test
    void contextLoads() {
        assertThat(errorController).isNotNull();
    }

    @Test
    void testShowErrorPage() {
        Exception exception = new IllegalArgumentException("Test Exception");

        String result = errorController.showErrorPage(model, exception);

        assertEquals("error", result);
        verify(model).addAttribute("errorMessage", "Test Exception");
    }

    @Test
    void testHandleNoHandlerFoundException() {
        String result = errorController.handleNoHandlerFoundException(model, new NoHandlerFoundException("req", "http", HttpHeaders.EMPTY));

        assertEquals("error", result);
        verify(model).addAttribute("errorMessage", "The page doesn't exist");
    }

}
