package ru.rti.holidays.converter;

import com.vaadin.data.ValueProvider;

public class StringValueProvider implements ValueProvider<String, String> {
    @Override
    public String apply(String s) {
        return s == null ? "" : "<p class=\"rti-wrap\">" + s + "</p>";
    }
}
