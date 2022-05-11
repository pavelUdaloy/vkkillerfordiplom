package by.diplom.killer.vk.exception;

import by.diplom.killer.vk.security.entity.ErrorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.constraints.NotNull;

import static java.util.Objects.isNull;

@RestControllerAdvice
@RequiredArgsConstructor
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseKillerException.class)
    public ResponseEntity<Object> handlePathology3dException(BaseKillerException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String title = exception.getErrorTitle();
        String errorCode = exception.getErrorKey();

        return fillResponseEntity(title, errorCode, status);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String title = exception.getErrorTitle();
        String errorCode = BaseKillerException.DEFAULT_MSG;

        return fillResponseEntity(title, errorCode, status);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception) {
        String title = exception.getMessage();
        String errorCode = BaseKillerException.DEFAULT_MSG;

        return fillResponseEntity(title, errorCode, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleInternalException(Exception exception) {
        String title = exception.getMessage();
        String errorCode = BaseKillerException.DEFAULT_MSG;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return fillResponseEntity(title, errorCode, status);
    }

    @NotNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NotNull MethodArgumentNotValidException ex,
                                                                  @NotNull HttpHeaders headers,
                                                                  @NotNull HttpStatus status,
                                                                  @NotNull WebRequest webRequest) {

        FieldError fieldError = ex.getBindingResult().getFieldError();
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (isNull(fieldError) || isNull(requestAttributes)) {
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }

        String message = ex.getMessage();

        return fillResponseEntity(
                BaseKillerException.DEFAULT_MSG,
                message,
                HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> fillResponseEntity(String errorTitle, String errorCode, HttpStatus status) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(errorCode);
        errorDto.setTitle(errorTitle);

        return ResponseEntity.status(status)
                .body(errorDto);
    }
}
