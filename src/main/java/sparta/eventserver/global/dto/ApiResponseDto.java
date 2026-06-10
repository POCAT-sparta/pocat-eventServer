package sparta.eventserver.global.dto;


public interface ApiResponseDto<T> {

    static <T> ApiResponseDto<T> error(String code, String message) {
        return new ErrorDto<>(code, message, null);
    }
}
