package ru.rti.holidays.greeter;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@SpringComponent
@UIScope
/**
 * @deprecated Remove later
 */
public class Greeter {
    public String sayHello() {
        return "Hello from bean " + toString();
    }
}
