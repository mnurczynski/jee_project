package pl.edu.pg.eti.kask.rpg.controller.servlet.exception;

public class InternalServerException extends HttpRequestException {

    private static final int RESPONSE_CODE = 500;
    public InternalServerException() {
        super(RESPONSE_CODE);
    }

    public InternalServerException(String message) {
        super(message, RESPONSE_CODE);
    }

    /**
     * @param message the detail message
     * @param cause   the cause
     */
    public InternalServerException(String message, Throwable cause) {
        super(message, cause, RESPONSE_CODE);
    }

    /**
     * @param cause the cause
     */
    public InternalServerException(Throwable cause) {
        super(cause, RESPONSE_CODE);
    }

    /**
     * @param message            the detail message
     * @param cause              the cause
     * @param enableSuppression  whether suppression is enabled or disabled
     * @param writableStackTrace whether the stack trace should be writable
     */
    public InternalServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, RESPONSE_CODE);
    }
}
