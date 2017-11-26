package ru.rti.holidays.exception.handler;

import ru.rti.holidays.utility.UIHelper;

/**
 * Standard exception handler for Views in UI.
 * It shows the Red alert window containing the Error message
 */
public class ViewErrorMessageExceptionHandler extends AbstractExceptionHandler {

    public ViewErrorMessageExceptionHandler() {
        super();
    }

    public ViewErrorMessageExceptionHandler(ExceptionHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public void handle(Exception e, String errorMessage) {
        UIHelper.showError(errorMessage);
        super.handle(e, errorMessage);
    }
}
