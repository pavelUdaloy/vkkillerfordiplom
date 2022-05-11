package by.diplom.killer.vk.exception;

public class NotFoundException extends BaseKillerException {

    private static final String DEFAULT_MSG = "Not found entity.";

    public NotFoundException() {
        super(DEFAULT_MSG, "Sorry", "Smth not found");
    }

    public NotFoundException(String message) {
        super(message, "Sorry", "Not found");
    }

    public NotFoundException(String title, String errorKey, String message) {
        super(message, title, errorKey);
    }
}
