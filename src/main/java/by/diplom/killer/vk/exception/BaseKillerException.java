package by.diplom.killer.vk.exception;

import lombok.Getter;

@Getter
public class BaseKillerException extends RuntimeException {

    public final static String DEFAULT_MSG = "Internal server error";
    public final static String DEFAULT_TITLE = "Sorry";

    protected String errorKey;
    protected String errorTitle;

    public BaseKillerException(String errorKey) {
        super(DEFAULT_MSG);
        this.errorTitle = DEFAULT_TITLE;
        this.errorKey = errorKey;
    }

    public BaseKillerException(String title, String errorKey) {
        super(DEFAULT_MSG);
        this.errorTitle = title;
        this.errorKey = errorKey;
    }

    public BaseKillerException(String message, String title, String errorKey) {
        super(message);
        this.errorTitle = title;
        this.errorKey = errorKey;
    }

}
