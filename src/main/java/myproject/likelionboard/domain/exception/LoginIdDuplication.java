package myproject.likelionboard.domain.exception;

public class LoginIdDuplication extends RuntimeException{

    public LoginIdDuplication() {
        super();
    }

    public LoginIdDuplication(String message) {
        super(message);
    }

    public LoginIdDuplication(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginIdDuplication(Throwable cause) {
        super(cause);
    }
}
