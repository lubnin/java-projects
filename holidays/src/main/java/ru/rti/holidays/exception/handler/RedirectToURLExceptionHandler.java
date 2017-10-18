package ru.rti.holidays.exception.handler;

import com.vaadin.server.Page;

public class RedirectToURLExceptionHandler extends AbstractExceptionHandler {
    private String redirectUrl;

    public RedirectToURLExceptionHandler() {
        super();
    }

    public RedirectToURLExceptionHandler(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public RedirectToURLExceptionHandler(ExceptionHandler nextHandler) {
        super(nextHandler);
    }

    public RedirectToURLExceptionHandler(String redirectUrl, ExceptionHandler nextHandler) {
        this(redirectUrl);
        setNextHandler(nextHandler);
    }

    @Override
    public void handle(Exception e, String errorMessage) {
        Page.getCurrent().setLocation(redirectUrl);
        super.handle(e, errorMessage);
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
