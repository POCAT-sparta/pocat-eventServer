package sparta.eventserver.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sparta.eventserver.global.dto.ApiResponseDto;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 409 - 데이터 무결성 위반 (중복 데이터 등)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponseDto.error(
                        "DATA_INTEGRITY_VIOLATION",
                        "이미 존재하는 데이터이거나 데이터 제약 조건을 위반했습니다."
                ));
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleServiceException(ServiceException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ApiResponseDto.error(
                        ex.getCode(),
                        ex.getMessage()
                ));
    }
}
