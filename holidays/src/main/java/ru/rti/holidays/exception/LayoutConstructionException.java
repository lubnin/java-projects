package ru.rti.holidays.exception;

/**
 * This type of exception is thrown from layout classes under 'ru.rti.holidays.layout' package.
 * Exception is thrown when there are some problems and errors in the process of layout construction
 * in the methods 'constructLayout' and 'postConstructLayout' of BaseLayout implementations (for example,
 * in 'BaseVerticalLayout' class
 * @see ru.rti.holidays.layout.base.BaseLayout
 * @see ru.rti.holidays.layout.base.BaseVerticalLayout
 * @deprecated
 */
public class LayoutConstructionException extends Exception {

    private String detailMessage;

    public LayoutConstructionException(Class cls) {
        detailMessage = String.format("An error occured while constructing the Layout for class '%s'", cls.getCanonicalName());
    }

    public LayoutConstructionException(String message) {
        if (message == null) {
            throw new IllegalArgumentException(String.format("A message cannot be `null` for constructor of '%s' class", this.getClass().getCanonicalName()));
        }
        detailMessage = message;
    }

    @Override
    public String getMessage() {
        return detailMessage;
    }
}
