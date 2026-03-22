package project_management_system.demo.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.executable.ValidateOnExecution;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import project_management_system.demo.dto.ApiError;
import project_management_system.demo.exception.BadRequestException;
import project_management_system.demo.exception.ForbiddenException;
import project_management_system.demo.exception.NotFoundException;
import project_management_system.demo.exception.UnAuthorizedException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handldNotFound(NotFoundException ex, HttpServletRequest request){
        ApiError apiError = ApiError.builder()
                .error("NOT_FOUND")
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex, HttpServletRequest request){
        ApiError apiError = ApiError.builder()
                .error("BAD_REQUEST")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiError> handleForbidden(ForbiddenException ex, HttpServletRequest request){
        ApiError apiError = ApiError.builder()
                .error("FORBIDDEN")
                .status(HttpStatus.FORBIDDEN.value())
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
    }
    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ApiError> handleUnauthorized(UnAuthorizedException ex, HttpServletRequest request){
        ApiError apiError = ApiError.builder()
                .error("UNAUTHORIZED")
                .status(HttpStatus.FORBIDDEN.value())
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }
}
