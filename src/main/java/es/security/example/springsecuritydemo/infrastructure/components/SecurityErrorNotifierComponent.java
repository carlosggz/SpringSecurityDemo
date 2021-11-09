package es.security.example.springsecuritydemo.infrastructure.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.security.example.springsecuritydemo.application.handlers.ApiErrorResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityErrorNotifierComponent {
    private final ObjectMapper objectMapper;

    public void reportsSecurityError(
            HttpServletRequest request,
            HttpServletResponse response,
            RuntimeException exception,
            HttpStatus statusToReturn) throws IOException {

        logRequestInfo(request);
        logUserInformation();
        logErrorInformation(exception);

        //response.sendError(statusToReturn.value(), exception.getMessage());
        sendErrorInformation(response, statusToReturn, exception);
    }

    private void logRequestInfo(HttpServletRequest request) {
        log.error("Error in access to url {} with method {}", request.getRequestURL(), request.getMethod());
    }

    private void logUserInformation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.isAuthenticated()) {
            log.error("There is no user information");
        }
        else {
            log.error("User logged: {}", authentication.getName());
        }
    }

    private void logErrorInformation(Throwable ex) {
        final int MAX_LEVEL = 5;
        int currentLevel = 1;
        Throwable currentThrowable = ex;

        while(Objects.nonNull(currentThrowable) && currentLevel <= MAX_LEVEL) {
            log.error("Error at level {}: {}", currentLevel, currentThrowable.getMessage());
            currentThrowable = currentThrowable.getCause();
            currentLevel++;
        }
    }

    private void sendErrorInformation(HttpServletResponse response, HttpStatus statusToReturn, Throwable ex) throws IOException {
        ApiErrorResult toReturn = ApiErrorResult.builder()
                .statusCode(statusToReturn.value())
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .description(statusToReturn.getReasonPhrase())
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(statusToReturn.value());
        response.getWriter().println(objectMapper.writeValueAsString(toReturn));
    }
}
