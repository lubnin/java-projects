package ru.rti.holidays.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
//TODO: refactor this class later. For now it is not working properly
//@Controller
public class IndexController {

}
/*implements ErrorController {
    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String error() {
        return "ru.rti.holidays.view.error.ErrorDefaultView";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}*/
