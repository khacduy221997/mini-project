package grass.micro.apps.service.exception;

public class AccountNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -7163187913427048079L;

    public AccountNotFoundException() {
        super();
    }

    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(Throwable cause) {
        super(cause);
    }

}
