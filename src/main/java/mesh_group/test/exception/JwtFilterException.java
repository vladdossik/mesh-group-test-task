package mesh_group.test.exception;

public class JwtFilterException extends RuntimeException {
    public JwtFilterException(String message) {
        super(message);
    }

    public JwtFilterException(String message, Throwable cause) {
        super(message, cause);
    }
}
