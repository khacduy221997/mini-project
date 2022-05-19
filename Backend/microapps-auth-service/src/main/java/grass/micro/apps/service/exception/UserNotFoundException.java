package grass.micro.apps.service.exception;

public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -7163187913427048079L;

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

}
