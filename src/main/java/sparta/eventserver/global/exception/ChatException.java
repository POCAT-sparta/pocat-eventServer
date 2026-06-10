package sparta.eventserver.global.exception;

public class ChatException extends ServiceException {

    public ChatException(ErrorCode errorCode) {
        super(errorCode);
    }
}
